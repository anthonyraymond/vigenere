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
    public void shouldCalculateDistances() {
        final Repetition repetition = new Repetition("abc", newArrayList(3, 12, 25, 13));

        assertThat(repetition.getDistances()).containsOnly(9, 10, 22, 1, 13, 12);
    }

}
