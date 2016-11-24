package org.araymond.vigenere.cracker.kasiki;

import com.google.common.collect.Lists;
import org.araymond.vigenere.cracker.KeyLength;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by raymo on 24/11/2016.
 */
public class ProbableKeySelector {

    /**
     * Assuming that "We are looking for the key with the bigger length, but with a descend number of occurrences" this
     * method evict some unlikely keys.
     *
     * @param probableKey
     * @return
     */
    public List<KeyLength> removeUnlikelyKeys(final List<KeyLength> probableKey) {
        if (probableKey.size() < 2) {
            return probableKey;
        }
        final List<KeyLength> mostProbableKeys = this.deduplicateDivisors(probableKey);

        Collections.sort(mostProbableKeys, Comparator.comparingInt(KeyLength::getOccurrence).reversed());
        final ListIterator<KeyLength> it = mostProbableKeys.listIterator();
        KeyLength current = it.next();
        while (it.hasNext()) {
            final KeyLength next = it.next();
            if (next.getOccurrence() < current.getOccurrence() / 2 || next.getOccurrence() < mostProbableKeys.get(0).getOccurrence() / 3) {
                it.remove();
                it.previous();
            }

            current = next;
        }

        return mostProbableKeys;
    }

    /**
     * Due to the fact that "Key length are commons divisors between pairs of distances",
     * we can assume that if a there is a KeyLength with a length of 2 and another with a length of 4,
     * the occurrence of 2 was faked because 4 is also a divisor of 2. So the 2 has benefit from the '4' distance as well.
     * What we do here is that the redistribute properly the occurrences of the divisors.
     *
     * If there is a big length that is a divisor of a smaller length, we remove X occurrences from the small on, where X is the number of the big one occurence.
     *
     * Example:
     * If we have such KeyLength (with [KeyLength, NumberOfOccurence])
     * [2, 1000]
     * [6, 4500]
     * At the end we will output:
     * [2, -2500]
     * [6, 4800]
     *
     * @param keyLengths
     * @return
     */
    List<KeyLength> deduplicateDivisors(final List<KeyLength> keyLengths) {
        final List<KeyLength> mostProbableKeys = Lists.newArrayList();
        keyLengths.forEach(pk -> mostProbableKeys.add(new KeyLength(pk.getLength(), pk.getOccurrence())));
        Collections.sort(mostProbableKeys, Comparator.comparingInt(KeyLength::getLength));

        for (int i = 0; i < mostProbableKeys.size(); ++i) {
            for (int j = i + 1; j < mostProbableKeys.size(); j++) {
                final KeyLength smallerLength = mostProbableKeys.get(i);
                final KeyLength greaterLength = mostProbableKeys.get(j);
                if (greaterLength.getLength() % smallerLength.getLength() == 0) {
                    smallerLength.setOccurrence(smallerLength.getOccurrence() - greaterLength.getOccurrence());
                }
            }
        }

        return mostProbableKeys;
    }

}
