package org.araymond.vigenere.cracker.frequency.analysis;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.araymond.vigenere.cracker.utils.VigenereStringUtils;

import java.util.Map;

/**
 * Created by raymo on 17/12/2016.
 */
public abstract class FrequencyAnalysis {

    private final Map<String, Double> letterFrequency;

    protected FrequencyAnalysis(final Map<String, Double> letterFrequency) {
        Preconditions.checkNotNull(letterFrequency);
        Preconditions.checkArgument(letterFrequency.size() == 26, "letterFrequency Map has to contains 26 characters.");
        final Double sumOfFrequency = letterFrequency.values().stream().mapToDouble(Double::doubleValue).sum();
        Preconditions.checkArgument(
                ((sumOfFrequency > 0.97D) && (sumOfFrequency < 1.03D)),
                "Sum of the frequency have to be close to one, '" + sumOfFrequency + "' given."
        );
        this.letterFrequency = letterFrequency;
    }

    /**
     * Calculate the difference between english letter frequency and the given text letter frequencies.
     *
     * @param text text to perform frequency analysis onto.
     * @return a value between 0 and 1 (0 means the text match the english's characters frequency perfectly).
     */
    public Double deviationFromLanguage(final String text) {
        // Count how much time each character is repeated
        final Map<String, Long> textFrequencies = VigenereStringUtils.countRepetitionByCharacters(text);

        // If a character was missing from the table, add it with 0 occurrences.
        for (char c = 'a'; c <= 'z'; ++c) {
            final String currentChar = String.valueOf(c);
            if(!textFrequencies.containsKey(currentChar)) {
                textFrequencies.put(currentChar, 0L);
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
                    final Double difference = Math.abs(frequency - letterFrequency.get(character));
                    // Add a weight to the return relative to the english frequency of the character.
                    return difference / (1 + letterFrequency.get(character));
                })
                .sum();
    }


}
