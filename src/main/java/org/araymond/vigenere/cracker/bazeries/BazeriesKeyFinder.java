package org.araymond.vigenere.cracker.bazeries;

import com.google.common.collect.Maps;
import org.araymond.vigenere.VigenereCipher;
import org.araymond.vigenere.cracker.frequency.FrequencyAnalysis;
import org.araymond.vigenere.cracker.utils.VigenereStringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by raymo on 16/12/2016.
 */
public class BazeriesKeyFinder {

    private final VigenereCipher vigenere = new VigenereCipher();
    private final BazeriesKeyExtractor extractor = new BazeriesKeyExtractor();
    private final FrequencyAnalysis analysis = new FrequencyAnalysis();

    public List<String> findPossibleKeys(final String cipher, final String probableWord) {
        final String cipherNormalized = VigenereStringUtils.normalizePlainText(cipher);
        final String probableWordNormalized = VigenereStringUtils.normalizePlainText(probableWord);

        // as much as iteration as possible when cutting the text into part of probableWorld.length (with overlapping)
        return IntStream.rangeClosed(0, cipherNormalized.length() - probableWordNormalized.length())
                // get the substring from the whole text
                .mapToObj(i -> cipherNormalized.substring(i, i + probableWordNormalized.length()))
                // try to decrypt with the text so we may see the key that appear
                .map(maybeTheWord -> vigenere.uncypher(maybeTheWord, probableWordNormalized))
                // try to find a cyclic repetition
                .map(probableKey -> extractor.extractKeyFromCircularRepetitions(probableKey).orElse(null))
                // remove duplicates
                .distinct()
                // remove nulls
                .filter(w -> w != null)
                // identify best letter rotation for each possible key
                .map(probableKey -> this.findBestKeyRotation(cipher, probableKey))
                // returning it
                .collect(Collectors.toList());
    }

    /**
     * when the key is found with Bazeries method, it is not likely that the letters are in the correct order.
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
            rotations.put(builder.toString(), analysis.deviationFromEnglishLanguage(vigenere.uncypher(cipher, builder.toString())));

            builder
                    .append(builder.charAt(0))
                    .deleteCharAt(0);
        }
        return rotations.entrySet().stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .get()
                .getKey();
    }

}
