package org.araymond.vigenere.cracker.bazeries;

import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by raymo on 14/12/2016.
 */
class BazeriesKeyExtractor {

    //Group 0 is whole string, group 1 is repeated sequence at the begining of the string
    private final Pattern cyclicPattern = Pattern.compile("^(.+)[a-z]*\\1$");
    private final Pattern duplicatePattern = Pattern.compile("^([a-z]*?)\\1+$");

    public Optional<String> extractKeyFromCircularRepetitions(final String input) {
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

    private String removeDuplicateWords(final String input) {
        final Matcher matcher = duplicatePattern.matcher(input);
        if (!matcher.matches()) {
            return input;
        }

        return matcher.group(1);
    }

}
