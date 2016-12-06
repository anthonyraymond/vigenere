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
    public Map<String, Long> countSingleCharRepetition(final String text) {
        return Arrays.stream(text.toLowerCase().split(""))
                .filter(c -> !c.isEmpty())
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }

    /**
     * Given a {@code text}, it keeps only one character every {@code gap} value.
     * Exemple:
     *  - With text: abcdefghijkl
     *  - With startAt: 0
     *  - With gap: 3
     *  output= adgj
     * we keep character at index 0, 3, 6, 9.
     *
     * Sounds weird, but it allow picking one character every X characters.
     *
     * @param gap       The gap between two characters to pick.
     * @param startAt   Define which is the index of the first letter to start on.
     * @param text      The text to pick characters from.
     * @return a String composed of one characters every {@code gap} present into {@code text}
     */
    public String removeCharactersBetweenGap(final int gap, final int startAt, final CharSequence text) {
        final StringBuilder sb = new StringBuilder();
        for (int stringIndex = startAt; stringIndex < text.length(); stringIndex += gap) {
            sb.append(text.charAt(stringIndex));
        }
        return sb.toString();
    }

}
