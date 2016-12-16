package org.araymond.vigenere.cracker.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raymo on 26/11/2016.
 */
public class VigenereStringUtilsTest {

    @Test
    public void shouldNormalizeText() {
        final String withSpace = "  hello i got some spaces  ";
        Assertions.assertThat(VigenereStringUtils.normalizePlainText(withSpace)).isEqualTo("helloigotsomespaces");

        final String withCapitalCase = "I GOT SoMe capital Cases";
        assertThat(VigenereStringUtils.normalizePlainText(withCapitalCase)).isEqualTo("igotsomecapitalcases");

        final String withSpecialChar = "There !! is 'ééâ some ) special* /- 133 char here";
        assertThat(VigenereStringUtils.normalizePlainText(withSpecialChar)).isEqualTo("thereissomespecialcharhere");

        final String withNewLines = "hello\n i got\r\nsome\r";
        assertThat(VigenereStringUtils.normalizePlainText(withNewLines)).isEqualTo("helloigotsome");
    }

    @Test
    public void shouldCountSingleCharRepetition() {
        final String text = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";

        final Map<String, Long> counted = VigenereStringUtils.countRepetitionByCharacters(text);

        assertThat(counted).hasSize(26);
        assertThat(counted.values().stream().distinct().collect(Collectors.toList())).containsOnly(2L);
    }

    @Test
    public void shouldCountSingleCharRepetition2() {
        final Map<String, Long> charCount = VigenereStringUtils.countRepetitionByCharacters("abcdefghijklmnopqrstuvwxyzaaanqq");
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

        assertThat(VigenereStringUtils.peekAndLeap(4, 0, text)).isEqualTo("aeimq");
    }

    @Test
    public void shouldRemoveCharactersBetweenGapWithStartAt() {
        final String text = "abcdefghijklmnopqr";

        assertThat(VigenereStringUtils.peekAndLeap(4, 1, text)).isEqualTo("bfjnr");
    }

}
