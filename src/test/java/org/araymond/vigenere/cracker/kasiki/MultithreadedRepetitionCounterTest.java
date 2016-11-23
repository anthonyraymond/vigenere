package org.araymond.vigenere.cracker.kasiki;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raymo on 22/11/2016.
 */
public class MultithreadedRepetitionCounterTest {

    private final MultithreadedRepetitionCounter counter = new MultithreadedRepetitionCounter();

    @Test
    public void shouldFindRepetitionInSimpleText() throws ExecutionException, InterruptedException {
        final String fullText = "abcbpmxxbpmabc";

        final Collection<Repetition> repetitions = new ArrayList<>();
        repetitions.add(new Repetition("abc", Lists.newArrayList(0, 11)));
        repetitions.add(new Repetition("bpm", Lists.newArrayList(3, 8)));
        assertThat(counter.count(fullText)).containsOnlyElementsOf(repetitions);
    }

}
