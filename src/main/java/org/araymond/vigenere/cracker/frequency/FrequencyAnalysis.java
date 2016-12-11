package org.araymond.vigenere.cracker.frequency;

import com.google.common.collect.Maps;
import org.araymond.vigenere.VigenereCipher;
import org.araymond.vigenere.cracker.utils.VigenereStringUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by raymo on 29/11/2016.
 */
public class FrequencyAnalysis {

    private final VigenereCipher vigenere = new VigenereCipher();

    private static final Map<String, Double> englishFrequency = new HashMap<>(26);
    static {
        englishFrequency.put("a", 0.082D);
        englishFrequency.put("b", 0.015D);
        englishFrequency.put("c", 0.028D);
        englishFrequency.put("d", 0.043D);
        englishFrequency.put("e", 0.127D);
        englishFrequency.put("f", 0.022D);
        englishFrequency.put("g", 0.020D);
        englishFrequency.put("h", 0.061D);
        englishFrequency.put("i", 0.070D);
        englishFrequency.put("j", 0.002D);
        englishFrequency.put("k", 0.008D);
        englishFrequency.put("l", 0.040D);
        englishFrequency.put("m", 0.024D);
        englishFrequency.put("n", 0.067D);
        englishFrequency.put("o", 0.075D);
        englishFrequency.put("p", 0.019D);
        englishFrequency.put("q", 0.001D);
        englishFrequency.put("r", 0.060D);
        englishFrequency.put("s", 0.063D);
        englishFrequency.put("t", 0.091D);
        englishFrequency.put("u", 0.028D);
        englishFrequency.put("v", 0.010D);
        englishFrequency.put("w", 0.023D);
        englishFrequency.put("x", 0.001D);
        englishFrequency.put("y", 0.020D);
        englishFrequency.put("z", 0.001D);
    }

    public String breakKey(final CharSequence cypher, final Integer keyLength) {
        return IntStream.range(0, keyLength)
                .mapToObj(shift -> VigenereStringUtils.peekAndLeap(keyLength, shift, cypher))
                .map(this::findBestShiftToMatchEnglishFrequency)
                .map(Map.Entry::getKey)
                .reduce((i, j) -> String.join("", i, j))
                .get();
    }

    /**
     * Goes through all possible character 'from a to z' and try to find the best to decrypt, so that the result would
     * match english letter frequency as much as possible.
     *
     * @param subText
     * @return
     */
    Map.Entry<String, Double> findBestShiftToMatchEnglishFrequency(final String subText) {
        // For each characters in 'a' to 'z'
        return IntStream.rangeClosed(0, 25)
                //We uncypher the text with vigenere (with a key of length 1, so it's caesar) and calculate the deviation from english letter frequency
                .mapToObj(i -> {
                    final String currentShift = Character.toString((char)('a' + i));
                    final String uncyphered = vigenere.uncypher(subText, currentShift);

                    return Maps.immutableEntry(currentShift, deviationFromEnglishLanguage(uncyphered));
                })
                // the best match is our minimum deviation, so we keep this one only
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .get();
    }

    /**
     * Calculate the difference between english letter frequency and the given text letter frequencies.
     *
     * @param text
     * @return a value between 0 and 1 (0 means the text match the english's characters frequency perfectly).
     */
    Double deviationFromEnglishLanguage(final String text) {
        // Count how much time each character is repeated
        final Map<String, Long> textFrequencies = VigenereStringUtils.countRepetitionByCharacters(text);

        // If a character was missing from the table, add it with 0 occurrences.
        for (char c = 'a'; c <= 'z'; ++c) {
            if(!textFrequencies.containsKey(c + "")) {
                textFrequencies.put(c + "", 0L);
            }
        }

        return textFrequencies
                .entrySet().stream()
                // Turn character count into their frequency (based on total amount of letters in text)
                .map(entry -> {
                    final Double charFrequency = text.length() > 0 ? entry.getValue() / (double) text.length() : 0;
                    return Maps.immutableEntry(entry.getKey(), charFrequency);
                })
                // Then calculate difference between english letters occurrence frequency and our frequency
                .mapToDouble(entry -> {
                    final String character = entry.getKey();
                    final Double frequency = entry.getValue();
                    // Delta between english frequency and actual frequency
                    final Double difference = Math.abs(frequency - englishFrequency.get(character));
                    // Add a weight to the return relative to the english frequency of the character.
                    return difference / (1 + englishFrequency.get(character));
                })
                .sum();
    }

}
