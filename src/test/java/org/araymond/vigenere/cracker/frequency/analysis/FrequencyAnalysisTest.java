package org.araymond.vigenere.cracker.frequency.analysis;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

/**
 * Created by raymo on 17/12/2016.
 */
public class FrequencyAnalysisTest {

    private final Map<String, Double> validMap = ImmutableMap.<String, Double>builder()
            .put("a", 0.082D)
            .put("b", 0.015D)
            .put("c", 0.028D)
            .put("d", 0.043D)
            .put("e", 0.127D)
            .put("f", 0.022D)
            .put("g", 0.020D)
            .put("h", 0.061D)
            .put("i", 0.070D)
            .put("j", 0.002D)
            .put("k", 0.008D)
            .put("l", 0.040D)
            .put("m", 0.024D)
            .put("n", 0.067D)
            .put("o", 0.075D)
            .put("p", 0.019D)
            .put("q", 0.001D)
            .put("r", 0.060D)
            .put("s", 0.063D)
            .put("t", 0.091D)
            .put("u", 0.028D)
            .put("v", 0.010D)
            .put("w", 0.023D)
            .put("x", 0.001D)
            .put("y", 0.020D)
            .put("z", 0.001D)
            .build();

    @Test
    public void shouldNotBuildWithNullMap() {
        assertThatThrownBy(() -> {
            new FrequencyAnalysis(null) {
            };
        })
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldNotBuildWithLessThan26Chars() {
        final Map<String, Double> map = Maps.newHashMap(validMap);
        map.remove("a");

        assertThatThrownBy(() -> {
            new FrequencyAnalysis(map) {
            };
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("has to contains 26");
    }

    @Test
    public void shouldNotBuildWithMoreThan26Chars() {
        final Map<String, Double> map = Maps.newHashMap(validMap);
        map.put("!", 0.000D);

        assertThatThrownBy(() -> {
            new FrequencyAnalysis(map) {
            };
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("has to contains 26");
    }

    @Test
    public void shouldNotBuildWithLessMoreThan1() {
        final Map<String, Double> map = Maps.newHashMap(validMap);
        map.put("e", 0.00D);
        assertThatThrownBy(() -> {
            new FrequencyAnalysis(map) {
            };
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Sum of the frequency have to be close to one");
    }

    @Test
    public void shouldNotBuildWithSumMoreThan1() {
        final Map<String, Double> map = Maps.newHashMap(validMap);
        map.put("a", 0.99999D);
        assertThatThrownBy(() -> {
            new FrequencyAnalysis(map) {
            };
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Sum of the frequency have to be close to one");
    }

    @Test
    public void shouldBuild() {
        try {
            new FrequencyAnalysis(validMap) {
            };
        } catch (final Throwable ignore) {
            fail("No exception expected", ignore);
        }
    }

}
