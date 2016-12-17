package org.araymond.vigenere.cracker.bazeries;

import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by raymo on 14/12/2016.
 */
class BazeriesStringUtils {

    //Group 0 is whole string, group 1 is repeated sequence at the begining of the string
    private final Pattern cyclicPattern = Pattern.compile("^(.+)[a-z]*\\1$");
    private final Pattern duplicatePattern = Pattern.compile("^([a-z]*?)\\1+$");

    /**
     * Given a text as input, it try to find a circular (partial or full) repetition in this input.
     * If it does it remove the overlapping repetition and extract the word that repeat.
     * Example:
     *  - oursou <- has a cyclic repetition which is "ours"
     *  - smile <- has no cyclic repetitions
     *  - oursoursa <- has no cyclic repetitions
     *
     * @param input a small word to look for cyclic repetitions into
     * @return the extracted word that repeat if there is a cyclic repetition. {@link Optional#empty()} otherwise.
     */
    Optional<String> extractWordFromCircularRepetition(final String input) {
        final Matcher matcher = cyclicPattern.matcher(input);
        if (!matcher.matches() || matcher.groupCount() < 1) {
            return Optional.empty();
        }
        final MatchResult res = matcher.toMatchResult();

        // if we got oursoursours, at this point we got "oursours", so we need to get ride of all repetitions
        final String keyWithRepetitions = input.replaceFirst(res.group(1), "");

        return Optional.ofNullable(
                removeDuplicateWords(keyWithRepetitions)
        );
    }

    /**
     * Given a word. if it's composed only of the same pattern again and again, we only return it once.
     * Example :
     *  - oursoursoursoursours <- return ours
     *  - ours <- return ours
     *
     * @param input word to remove duplicates into.
     * @return the word deduped.
     */
    private String removeDuplicateWords(final String input) {
        final Matcher matcher = duplicatePattern.matcher(input);
        if (!matcher.matches()) {
            return input;
        }

        return matcher.group(1);
    }

}
