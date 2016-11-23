package org.araymond.vigenere.cracker.utils;

import org.araymond.vigenere.cracker.utils.CommonDivisors.CommonDivisorLimit;
import org.junit.Test;

import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raymo on 23/11/2016.
 */
public class CommonDivisorsTest {

    private final CommonDivisors commonDivisors = new CommonDivisors();

    @Test
    public void shoudFindAllCommonsDivisors() {
        final ArrayList<Integer> values = newArrayList(3, 12, 45, 26, 500, 20);

        assertThat(commonDivisors.findFor(values, new CommonDivisorLimit(100)))
                .containsExactlyInAnyOrder(2, 2, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 10, 20);
    }

    @Test
    public void shouldNotFindDivisorGreaterThanLimit() {
        assertThat(commonDivisors.findFor(10, 20, new CommonDivisorLimit(5)))
                .containsOnly(2, 5);
    }

    @Test
    public void shouldExcludeOneFromCommonsDivisor() {
        assertThat(commonDivisors.findFor(3, 7, new CommonDivisorLimit(7))).isEmpty();
    }
    
    @Test
    public void shouldFindCommonDivisors() {
        assertThat(commonDivisors.findFor(480, 960, new CommonDivisorLimit(960)))
                .containsExactly(2, 3, 4, 5, 6, 8, 10, 12, 15, 16, 20, 24, 30, 32, 40, 48, 60, 80, 96, 120, 160, 240, 480);

        assertThat(commonDivisors.findFor(960, 480, new CommonDivisorLimit(960)))
                .containsExactly(2, 3, 4, 5, 6, 8, 10, 12, 15, 16, 20, 24, 30, 32, 40, 48, 60, 80, 96, 120, 160, 240, 480);
    }

}
