package org.araymond.vigenere.cracker.kasiki;

import org.araymond.vigenere.cracker.KeyLength;
import org.araymond.vigenere.cracker.KeyLengthEstimator;
import org.araymond.vigenere.cracker.utils.CommonDivisors;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by raymo on 22/11/2016.
 */
public class KasikiKeyLengthEstimator implements KeyLengthEstimator {

    private final CommonDivisors commonDivisorsUtils = new CommonDivisors();

    /**
     * Estimate the probable length of the key (based on common divisors of the distance between words) using kasiki method
     *
     * @param encoded the encrypted text
     * @return A List of probable key length.
     */
    @Override
    public List<KeyLength> estimate(final String encoded) {
        Collection<Repetition> repetitions = new ArrayList<>();
        try {
            // We are getting going to get repetitions in the text
            repetitions = new MultithreadedRepetitionCounter().count(encoded);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // We find all commons divisors for all the distances between repetitions
        //   ask not to find divisors greater than 65, because we assume the key won't be more than 65 characters long
        final List<Integer> commonsDivisors = commonDivisorsUtils.findFor(
                repetitions.stream()
                        .flatMap(rep -> rep.getDistances().stream())
                        .collect(Collectors.toList()),
                new CommonDivisors.CommonDivisorLimit(65)
        );

        // with this commons divisors, we create a list of KeyLength, which correspond to probables key's length.
        // Remarks : we get the divisor 2 out of the field, i don't think someone would ever pick a 2 characters key.
        final List<KeyLength> keyLengths = commonsDivisors.stream()
                .filter(divisor -> divisor > 2)
                .collect(Collectors.groupingBy(divisor -> divisor, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new KeyLength(entry.getKey(), entry.getValue().intValue()))
                .collect(Collectors.toList());

        // To be able to exclude irrevelant entries from the list, we calculate the average value of the occurrences.
        // TODO : improve the exclusion process
        final Double averageOccurenceValue = keyLengths
                .stream()
                .mapToInt(KeyLength::getOccurence)
                .average()
                .orElse(1);

        // Finaly, we exclude non revelant entries from the list. (ie: [15, 14, 12, 2] 2 will be excluded because it is a lot above the rest
        return keyLengths
                .stream()
                .sorted(Comparator.comparingInt(KeyLength::getOccurence))
                .filter(keyLength -> keyLength.getOccurence() > averageOccurenceValue * 0.6)
                .collect(Collectors.toList());
    }

}
