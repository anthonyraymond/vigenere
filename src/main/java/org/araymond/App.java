package org.araymond;

import org.araymond.vigenere.VigenereCipher;
import org.araymond.vigenere.cracker.KeyLength;
import org.araymond.vigenere.cracker.KeyLengthEstimator;
import org.araymond.vigenere.cracker.bazeries.BazeriesKeyFinder;
import org.araymond.vigenere.cracker.frequency.FrequencyKeyBreaker;
import org.araymond.vigenere.cracker.friedman.FriedmanKeyLengthEstimator;
import org.araymond.vigenere.cracker.kasiski.KasikiKeyLengthEstimator;
import org.araymond.vigenere.cracker.utils.VigenereStringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * Hello world!
 */
public class App {

    private static final KeyLengthEstimator kasiskiEstimator = new KasikiKeyLengthEstimator();
    private static final KeyLengthEstimator friedmanEstimator = new FriedmanKeyLengthEstimator();
    private static final FrequencyKeyBreaker frequencyKeyBreaker = new FrequencyKeyBreaker();
    private static final VigenereCipher vigenere = new VigenereCipher();
    private static final BazeriesKeyFinder bazeries = new BazeriesKeyFinder();

    public static void main(final String[] args) {
        if (args.length < 1) {
            err.println("Type 'help' for vigenere usage.");
            System.exit(1);
        }

        switch (args[0]) {
            case "help":
                help();
                break;
            case "cypher":
                cyphering(args);
                break;
            case "uncypher":
                uncyphering(args);
                break;
            case "estimate":
                estimate(args);
                break;
            case "frequency":
                frequencyAnalysis(args);
                break;
            case "bazeries":
                bazeries(args);
                break;
            case "break":
                fullBreak(args);
                break;
            default:
                err.println("Type 'help' for vigenere usage.");
                break;
        }
        System.exit(0);
    }


    private static void help() {
        out.println("Usage: " + System.getProperty("line.separator") +
                "   - cypher <plaintText> <key>             : Encode a text using vigenere algorithm." + System.getProperty("line.separator") +
                "   - uncypher <encodedText> <key>          : Decode a text using vigenere algorithm." + System.getProperty("line.separator") +
                "   - estimate <encodedText>                : Estimate key lengths using Babbage and Kasiski method and Friendman test." + System.getProperty("line.separator") +
                "   - frequency <keyLength> <encodedText>   : Break a key using frequency analysis." + System.getProperty("line.separator") +
                "   - bazeries <probableWord> <encodedText> : Try to find the key with a word that is supossed to be in the text." + System.getProperty("line.separator") +
                "   - break <encodedText>                   : Take a cypher and try to find the key using kasiski and friendman, then frequency analysis. "
        );
    }

    private static void cyphering(final String[] args) {
        if (args.length != 3) {
            err.println("Cyphering syntax: cypher <plaintText> <key>");
            System.exit(1);
        }

        out.println(vigenere.cypher(args[1], args[2]));
    }

    private static void uncyphering(final String[] args) {
        if (args.length != 3) {
            err.println("Uncyphering syntax: uncypher <encodedText> <key>");
            System.exit(1);
        }

        final String encoded = VigenereStringUtils.normalizePlainText(args[1]);
        out.println(vigenere.uncypher(encoded, args[2]));
    }

    private static void estimate(final String[] args) {
        if (args.length != 2) {
            err.println("Estimating syntax syntax: estimate <encodedText>");
            System.exit(1);
        }

        final String encoded = VigenereStringUtils.normalizePlainText(args[1]);

        out.println("Possibles key lengths are :");
        out.println("    For Babbage and kasiski:");
        final List<KeyLength> kasiskiKeys = kasiskiEstimator.estimate(encoded);
        Collections.sort(kasiskiKeys, Comparator.comparingInt(KeyLength::getOccurrence).reversed());
        for (final KeyLength keyLength : kasiskiKeys) {
            final String toPrint = String.format("        - Key length: %2s  Occurences: %s", keyLength.getLength(), keyLength.getOccurrence());
            out.println(toPrint);
        }

        out.println("    For Friedman test:");
        out.println("        - Key length : " + friedmanEstimator.estimate(encoded).get(0).getLength());
    }

    private static void frequencyAnalysis(final String[] args) {
        if (args.length != 3) {
            err.println("Frequency analysis to break the key : frequency <keyLength> <encodedText>");
            System.exit(1);
        }

        final String encoded = VigenereStringUtils.normalizePlainText(args[2]);
        final Integer keyLength = Integer.valueOf(args[1]);

        final String key = frequencyKeyBreaker.breakKey(encoded, keyLength);
        out.print("Key is: " + key);
    }


    private static void bazeries(String[] args) {
        if (args.length != 3) {
            err.println("Perform a probable word attack : bazeries <probableWord> <encodedText>");
            System.exit(1);
        }

        final String probableWord = VigenereStringUtils.normalizePlainText(args[1]);
        final String encoded = VigenereStringUtils.normalizePlainText(args[2]);

        final List<String> possibleKeys = bazeries.findPossibleKeys(encoded, probableWord);
        if (possibleKeys.isEmpty()) {
            out.println("No Keys were found");
        }
        possibleKeys.forEach(key -> out.println("     - " + key));
    }

    private static void fullBreak(final String[] args) {
        if (args.length != 2) {
            err.println("Estimating syntax syntax: break <encodedText>");
            System.exit(1);
        }

        final String encoded = VigenereStringUtils.normalizePlainText(args[1]);

        out.println("Possible keys can be :");
        final List<String> possibleKeys = Stream.concat(
                kasiskiEstimator.estimate(encoded).stream(),
                friedmanEstimator.estimate(encoded).stream()
        )
                .map(KeyLength::getLength)
                .collect(Collectors.toSet()).stream()
                .map(length -> frequencyKeyBreaker.breakKey(encoded, length))
                .collect(Collectors.toList());

        if (possibleKeys.isEmpty()) {
            out.println("No Keys were found");
        }
        possibleKeys.forEach(key -> out.println("     - " + key));


    }

}
