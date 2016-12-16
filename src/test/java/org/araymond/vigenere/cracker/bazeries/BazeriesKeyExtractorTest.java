package org.araymond.vigenere.cracker.bazeries;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raymo on 14/12/2016.
 */
public class BazeriesKeyExtractorTest {

    private final BazeriesKeyExtractor extractor = new BazeriesKeyExtractor();

    @Test
    public void shouldReturnEmptyIfStringHasNotCircularRepetitions() {
        final String str = "abcdefghijkl";
        assertThat(extractor.extractKeyFromCircularRepetitions(str)).isEmpty();
    }

    @Test
    public void shouldExtractCircularDependency() {
        final String str = "rsours";
        assertThat(extractor.extractKeyFromCircularRepetitions(str)).hasValue("ours");
    }

    @Test
    public void shouldExtractCircularDependency2() {
        final String str = "arctiquea";
        assertThat(extractor.extractKeyFromCircularRepetitions(str)).hasValue("rctiquea");
    }

    @Test
    public void shouldExtractCircularDependency3() {
        final String str = "tiquearcti";
        assertThat(extractor.extractKeyFromCircularRepetitions(str)).hasValue("quearcti");
    }

    @Test
    public void shouldExtractCircularDependency4() {
        final String str = "arctiquearc";
        assertThat(extractor.extractKeyFromCircularRepetitions(str)).hasValue("tiquearc");
    }

    @Test
    public void shouldExtractCircularDependencyEvenIfThereIsANestedRepetition() {
        final String str = "abczdfzga";
        assertThat(extractor.extractKeyFromCircularRepetitions(str)).hasValue("bczdfzga");
    }

    @Test
    public void shouldExtractCircularDependencyIfRepeatMoreThanOneTime() {
        final String str = "rsoursoursou";
        assertThat(extractor.extractKeyFromCircularRepetitions(str)).hasValue("rsou");
    }

    @Test
    public void shouldExtractCircularDependencyIfRepeatMoreThanOneTime2() {
        final String str = "soursours";
        assertThat(extractor.extractKeyFromCircularRepetitions(str)).hasValue("ours");
    }

}
