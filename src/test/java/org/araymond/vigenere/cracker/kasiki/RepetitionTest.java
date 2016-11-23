package org.araymond.vigenere.cracker.kasiki;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raymo on 22/11/2016.
 */
public class RepetitionTest {

    @Test
    public void shouldComputeSimpleCommonsDivisors() {
        final Repetition repetition = new Repetition("abc", newArrayList(3, 12, 45, 26, 500, 20));

        assertThat(repetition.getCommonDivisors()).isEqualTo(Lists.newArrayList(3, 3, 3, 2, 2, 4, 2, 4, 5, 5, 2, 2, 2, 4, 5, 10, 20));
    }

}
