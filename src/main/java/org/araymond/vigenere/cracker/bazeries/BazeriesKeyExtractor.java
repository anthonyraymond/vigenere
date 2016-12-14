package org.araymond.vigenere.cracker.bazeries;

import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by raymo on 14/12/2016.
 */
public class BazeriesKeyExtractor {

    //Group 0 is whole string, group 1 is repeated sequence at the begining of the string
    private final Pattern pattern = Pattern.compile("^(.+)[a-z]+\\1$");


    public Optional<String> extractKeyFromCircularRepetitions(final String input) {
        final Matcher matcher = pattern.matcher(input);
        if (!matcher.matches() || matcher.groupCount() < 1) {
            return Optional.empty();
        }
        final MatchResult res = matcher.toMatchResult();


        return Optional.of(
                input.replaceFirst(res.group(1), "")
        );
    }

}
