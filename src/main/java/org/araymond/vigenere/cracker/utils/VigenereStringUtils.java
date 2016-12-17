package org.araymond.vigenere.cracker.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by raymo on 26/11/2016.
 */
public class VigenereStringUtils {

    /**
     * Take a string as a parameter and :
     *  - Remove spaces.
     *  - Remove all non alphabetical characters.
     *  - Lowercase all characters.
     *
     * @param plaintText a String that may contains unwanted chars
     * @return A String without all unwanted characters
     */
    public static String normalizePlainText(final String plaintText) {
        return plaintText.replaceAll("[^A-Za-z]+", "").toLowerCase();
    }

    /**
     * Count how much time each characters occurs in a text.
     *
     * @param text the text to count characters into.
     * @return a Map with the key being a character and the value how much time it occurs
     */
    public static Map<String, Long> countRepetitionByCharacters(final String text) {
        return Arrays.stream(text.split(""))
                .filter(c -> !c.isEmpty())
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }

    /**
     * Given a {@code text}, it keeps only one character every {@code leap} value.
     * Example:
     *  - With text: abcdefghijkl
     *  - With startAt: 0
     *  - With leap: 3
     *  output= adgj
     * we keep character at index 0, 3, 6, 9.
     *
     * Sounds weird, but it allow picking one character every X characters.
     *
     * @param leap       The leap between two characters to pick.
     * @param startAt   Define which is the index of the first letter to start on.
     * @param text      The text to pick characters from.
     * @return a String composed of one characters every {@code leap} present into {@code text}
     */
    public static String peekAndLeap(final int leap, final int startAt, final CharSequence text) {
        final StringBuilder sb = new StringBuilder();
        for (int stringIndex = startAt; stringIndex < text.length(); stringIndex += leap) {
            sb.append(text.charAt(stringIndex));
        }
        return sb.toString();
    }

}
