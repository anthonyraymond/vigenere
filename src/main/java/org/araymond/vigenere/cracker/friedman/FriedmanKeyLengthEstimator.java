package org.araymond.vigenere.cracker.friedman;

import org.araymond.vigenere.cracker.KeyLength;
import org.araymond.vigenere.cracker.KeyLengthEstimator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by raymo on 23/11/2016.
 */
public class FriedmanKeyLengthEstimator implements KeyLengthEstimator {

    private final FriedmanStringUtils utils = new FriedmanStringUtils();

    @Override
    public List<KeyLength> estimate(final String encoded) {
        final Double initialIndexOfCoincidence = computeIndexOfCoincidence(encoded);
        final Integer initialLength = computeLengthFromIndexOfCoincidence(initialIndexOfCoincidence);

        final Integer optimalLength = improveLengthApproximation(initialIndexOfCoincidence, initialLength, encoded);

        return Collections.singletonList(new KeyLength(optimalLength, 0));
    }

    /**
     * There is a theory in wikipedia that says : <br>
     * <i>A better approach for repeating-key ciphers is to copy the ciphertext into rows of a matrix having as
     * many columns as an assumed key length, then compute the average index of coincidence with each column
     * considered separately; when this is done for each possible key length, the highest average I.C. then
     * corresponds to the most likely key length.</i><br>
     * <br>
     * This is what this function is about, we are going to try keys up to 6 characters longer than the initial key.
     *
     *
     * @param initialIndexOfCoincidence     index of coincidence to improve.
     * @param initialLength                 length of the key before improvements.
     * @param encoded                       vigenere encoded text
     * @return improved or not (but most likely improved Length of the vigenere key.
     */
    private Integer improveLengthApproximation(final Double initialIndexOfCoincidence, final Integer initialLength, final CharSequence encoded) {
        Double optimalIndexOfCoincidence = initialIndexOfCoincidence;
        Integer optimalLength = initialLength;
        for (int probableLength = initialLength + 1; probableLength < initialLength + 6; ++probableLength) {
            // This is basicaly the number of "line" in the matrix, there is no particular reason we redefine a variable, but it improve readability
            final int numberOfSplits = probableLength;

            // Since we want to work on column, <e are gong to simulate the "matrix" by picking one character out of X characters in string.
            final Double currentIndexOfCoincidence = IntStream.rangeClosed(0, numberOfSplits)
                    .mapToObj(shift -> utils.removeCharactersBetweenGap(numberOfSplits, shift, encoded))
                    // Then we get the index of coincidence of each "column" to calculate the average value.
                    .map(this::computeIndexOfCoincidence)
                    .collect(Collectors.averagingDouble(d -> d));

            // If we match a better solution than we had before, we keep a record of it.
            if (currentIndexOfCoincidence > optimalIndexOfCoincidence) {
                optimalIndexOfCoincidence = currentIndexOfCoincidence;
                optimalLength = probableLength;
            }
        }
        return optimalLength;
    }

    /**
     * Calculate the length of the key (using Friedman method) from an index of coincidence.
     *
     * @param indexOfCoincidence    index of coincidence.
     * @return the length of the vigenere key.
     */
    private Integer computeLengthFromIndexOfCoincidence(final Double indexOfCoincidence) {
        return (int) Math.floor((0.067D - 0.0385D)/(indexOfCoincidence - 0.0385D));
    }
    /**
     * Calculate the index of coincidence of a text.
     *
     * @param text  text to compute the index of coincidence from.
     * @return the index of coincidence of the text.
     */
    private Double computeIndexOfCoincidence(final String text) {
        final double incidenceOfCoincidence = utils.countSingleCharRepetition(text)
                .values().stream()
                .mapToInt(letterCount -> letterCount.intValue() * (letterCount.intValue() - 1))
                .sum();

        return incidenceOfCoincidence / ((text.length() * (text.length() - 1D)));
    }



}
