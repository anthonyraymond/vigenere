package org.araymond.vigenere.cracker.friedman;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raymo on 26/11/2016.
 */
public class FriedmanStringUtilsTest {

    private final FriedmanStringUtils utils = new FriedmanStringUtils();

    @Test
    public void shouldCountSingleCharRepetition() {
        final String text = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";

        final Map<String, Long> counted = utils.countSingleCharRepetition(text);

        assertThat(counted).hasSize(26);
        assertThat(counted.values().stream().distinct().collect(Collectors.toList())).containsOnly(2L);
    }

    @Test
    public void shouldCountSingleCharRepetition2() {
        final Map<String, Long> charCount = utils.countSingleCharRepetition("abcdefghijklmnopqrstuvwxyzaaanqq");
        final Map<String, Long> expected = new HashMap<>();
        for (char c = 'a'; c <= 'z'; ++c) {
            expected.put(c + "", 1L);
        }
        expected.put("a", 4L);
        expected.put("n", 2L);
        expected.put("q", 3L);

        assertThat(charCount).isEqualTo(expected);
    }

    @Test
    public void shouldRemoveCharactersBetweenGap() {
        final String text = "abcdefghijklmnopqr";

        assertThat(utils.removeCharactersBetweenGap(4, 0, text)).isEqualTo("aeimq");
    }

    @Test
    public void shouldRemoveCharactersBetweenGapWithStartAt() {
        final String text = "abcdefghijklmnopqr";

        assertThat(utils.removeCharactersBetweenGap(4, 1, text)).isEqualTo("bfjnr");
    }

}
