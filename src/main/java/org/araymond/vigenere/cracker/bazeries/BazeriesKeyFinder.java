package org.araymond.vigenere.cracker.bazeries;

import com.google.common.collect.Maps;
import org.araymond.vigenere.VigenereCipher;
import org.araymond.vigenere.cracker.frequency.analysis.FrequencyAnalysis;
import org.araymond.vigenere.cracker.frequency.analysis.impl.EnglishFrequencyAnalysis;
import org.araymond.vigenere.cracker.utils.VigenereStringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by raymo on 16/12/2016.
 */
public class BazeriesKeyFinder {

    private final VigenereCipher vigenere = new VigenereCipher();
    private final BazeriesStringUtils extractor = new BazeriesStringUtils();
    private final FrequencyAnalysis analysis = new EnglishFrequencyAnalysis();

    /**
     * Find all possible keys for for the bazeries method.
     *
     * @param cipher
     * @param probableWord
     * @return
     */
    public List<String> findPossibleKeys(final String cipher, final String probableWord) {
        final String cipherNormalized = VigenereStringUtils.normalizePlainText(cipher);
        final String probableWordNormalized = VigenereStringUtils.normalizePlainText(probableWord);

        // as much as iteration as possible when cutting the text into part of probableWorld.length (with overlapping)
        return IntStream.rangeClosed(0, cipherNormalized.length() - probableWordNormalized.length())
                // get the substring from the whole text (hoping to find the probable word)
                .mapToObj(i -> cipherNormalized.substring(i, i + probableWordNormalized.length()))
                // try to decrypt the substring with the probableWord, this way we may get the key back
                .map(maybeTheWord -> vigenere.uncypher(maybeTheWord, probableWordNormalized))
                // try to find a cyclic repetition. if there were not, returns null
                .map(probableKey -> extractor.extractWordFromCircularRepetition(probableKey).orElse(null))
                // remove duplicates
                .distinct()
                // remove nulls (generated 2 steps earlier)
                .filter(Objects::nonNull)
                // identify best letter rotation for each possible key
                .map(probableKey -> this.findBestKeyRotation(cipher, probableKey))
                // returning it
                .collect(Collectors.toList());
    }

    /**
     * When the key is found with Bazeries method, it is not likely that the letters are in the correct order.
     * Instead of "ours" we can have "rsou", so we are going to generate all the rotation of this word and see which
     * rotation seems to be the best one. (Evaluating rotation with english frequency analysis)
     *
     * @param cipher
     * @param key
     * @return
     */
    private String findBestKeyRotation(final String cipher, final String key) {
        final Map<String, Double> rotations = Maps.newHashMap();
        final StringBuilder builder = new StringBuilder(key);

        for (int i = 0; i < key.length(); i++) {
            // With the given rotation supposed to be the key, we uncipher the text and perform a frequency analysis
            final String uncypher = vigenere.uncypher(cipher, builder.toString());
            rotations.put(builder.toString(), analysis.deviationFromLanguage(uncypher));

            // Then we perform a left-shift on the string for the next round.
            builder
                    .append(builder.charAt(0))
                    .deleteCharAt(0);
        }
        // Key only the best rotation (minimum deviation from english language)
        return rotations.entrySet().stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .get()
                .getKey();
    }

}
