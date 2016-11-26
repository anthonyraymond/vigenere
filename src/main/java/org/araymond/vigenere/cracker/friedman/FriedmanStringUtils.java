package org.araymond.vigenere.cracker.friedman;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by raymo on 26/11/2016.
 */
public class FriedmanStringUtils {

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

    /**
     * Given a {@code text}, it keeps only one character every {@code gap} value.
     * Exemple:
     *  - With text: abcdefghijkl
     *  - With startAt: 0
     *  - With gap: 3
     *  output= adgj
     * we keep character 0, 3, 6, 9.
     *
     * @param gap       The gap between two characters to pick.
     * @param startAt   Define which is the index of the first letter to start on.
     * @param text      The text to pick characters from.
     * @return a String composed of one characters every {@code gap} present into {@code text}
     */
    String removeCharactersBetweenGap(final int gap, final int startAt, final CharSequence text) {
        final StringBuilder sb = new StringBuilder();
        for (int stringIndex = startAt; stringIndex < text.length(); stringIndex += gap) {
            sb.append(text.charAt(stringIndex));
        }
        return sb.toString();
    }

}
