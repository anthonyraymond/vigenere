package org.araymond.vigenere.cracker.kasiki;

import org.araymond.vigenere.cracker.KeyLength;
import org.araymond.vigenere.cracker.KeyLengthEstimator;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by raymo on 22/11/2016.
 */
public class KasikiKeyLengthEstimator implements KeyLengthEstimator {

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
            // We are getting commons divisors for each repetitions
            repetitions = new RepetitionCounter().count(encoded);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // with this commons divisors, we create a list of KeyLength, which correspond to probables key's length.
        // Then we sort the map and keep only first five best match
        // Remarks : we get the divisor 2 out of the field, i do'nt think someone would ever pick a 2 characters key.
        final List<KeyLength> keyLengths = repetitions
                .stream()
                .flatMap(r -> r.getCommonDivisors().stream())
                .filter(divisor -> divisor > 2)
                .collect(Collectors.groupingBy(divisor -> divisor, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(5)
                .map(entry -> new KeyLength(entry.getKey(), entry.getValue().intValue()))
                .collect(Collectors.toList());

        // To be able to exclude irrevelant entries from the list, we calculate the average value of the occurrences.
        // TODO : improve the exclusion process
        final Double averageOccurenceValue = keyLengths
                .stream()
                .mapToInt(KeyLength::getOccurence)
                .average()
                .orElse(1);

        // Finaly, we exclude non revelant entries from the list. (ie: [15, 14, 12, 6] 6 will be excluded because it is a lot above the rest
        return keyLengths
                .stream()
                .filter(keyLength -> keyLength.getOccurence() > averageOccurenceValue * 0.8)
                .collect(Collectors.toList());
    }

}
