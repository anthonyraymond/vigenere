package org.araymond.vigenere.cracker.kasiki;

import org.araymond.vigenere.cracker.KeyLength;
import org.araymond.vigenere.cracker.KeyLengthEstimator;
import org.araymond.vigenere.cracker.utils.CommonDivisors;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by raymo on 22/11/2016.
 */
public class KasikiKeyLengthEstimator implements KeyLengthEstimator {

    private final CommonDivisors.CommonDivisorLimit maxDivisor = new CommonDivisors.CommonDivisorLimit(20);
    private final CommonDivisors commonDivisorsUtils = new CommonDivisors(maxDivisor);
    private final ProbableKeySelector selector = new ProbableKeySelector();

    /**
     * Estimate the probable length of the key (based on common divisors of the distance between words) using kasiki method
     *
     * @param encoded the encrypted text
     * @return A List of probable key length.
     */
    @Override
    public List<KeyLength> estimate(final String encoded) {
        final List<KeyLength> probableKeys;
        try {
            // Gather all text repetition from the cypher
            probableKeys = new MultithreadedRepetitionCounter().count(encoded).parallelStream()
                    // Based on text repetition, we find distance between repetition and common divisors between these distances
                    .map(Repetition::getDistances)
                    .flatMap(commonDivisorsUtils::findFor)
                    // with this commons divisors, we create a list of KeyLength, which correspond to probables key's length.
                    // Remarks : we get the divisor 2 out of the field, i don't think someone would ever pick a 2 characters key.
                    .collect(Collectors.groupingBy(divisor -> divisor, Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> new KeyLength(entry.getKey(), entry.getValue().intValue()))
                    .collect(Collectors.toList());
        } catch (ExecutionException | InterruptedException e) {
            throw new IllegalArgumentException(e);
        }

        // Finally, we exclude non irrelevant keys from the list.
        return selector.removeUnlikelyKeys(probableKeys);
    }

}
