package org.araymond.vigenere.cracker.kasiki;

import org.araymond.vigenere.cracker.KeyLength;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raymo on 24/11/2016.
 */
public class ProbableKeySelectorTest {

    private final ProbableKeySelector selector = new ProbableKeySelector();

    @Test
    public void shouldNotFailWithEmptyList() {
        assertThat(selector.removeUnlikelyKeys(Lists.emptyList())).isEmpty();
    }

    @Test
    public void shouldNotFailWithOneLengthList() {
        final List<KeyLength> keyLengths = Lists.newArrayList(new KeyLength(5, 32));
        assertThat(selector.removeUnlikelyKeys(keyLengths)).containsOnlyElementsOf(keyLengths);
    }

    @Test
    public void shouldReturnNewListWithOnlyRevelentElements() {
        final List<KeyLength> keyLengths = Lists.newArrayList(
                new KeyLength(7, 192232),
                new KeyLength(14, 189976),
                new KeyLength(4, 55159),
                new KeyLength(28, 47731),
                new KeyLength(3, 27960),
                new KeyLength(6, 23574),
                new KeyLength(21, 20847),
                new KeyLength(42, 20658),
                new KeyLength(8, 13819),
                new KeyLength(56, 11596),
                new KeyLength(5, 10410),
                new KeyLength(10, 8304),
                new KeyLength(35, 7476)
                );

        assertThat(selector.removeUnlikelyKeys(keyLengths)).containsOnly(new KeyLength(14, 109991));
    }

}
