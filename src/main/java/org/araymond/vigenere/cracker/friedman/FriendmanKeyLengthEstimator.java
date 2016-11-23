package org.araymond.vigenere.cracker.friedman;

import org.araymond.vigenere.cracker.KeyLength;
import org.araymond.vigenere.cracker.KeyLengthEstimator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by raymo on 23/11/2016.
 */
public class FriendmanKeyLengthEstimator implements KeyLengthEstimator {

    @Override
    public List<KeyLength> estimate(final String encoded) {
        final Double coincidenceRate = computeCoincidenceRate(encoded);

        final Double length = (0.067 - 0.0385)/(coincidenceRate - 0.0385);

        return Collections.singletonList(new KeyLength((int) Math.round(length), 0));
    }

    /**
     * Count how much time each characters occurs.
     *
     * @param text
     * @return
     */
    Map<String, Long> countSingleCharRepetition(final String text) {
        return Arrays.stream(text.toLowerCase().split(""))
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

    }

    private Double computeCoincidenceRate(final String encodedText) {
        final double incidenceOfCoincidence = countSingleCharRepetition(encodedText)
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .mapToInt(entry -> entry.getValue().intValue() * (entry.getValue().intValue() - 1))
                .sum();

        return incidenceOfCoincidence / ((encodedText.length() * (encodedText.length() - 1D)));
    }



}
