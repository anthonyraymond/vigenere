package org.araymond;

import org.araymond.vigenere.VigenereCipher;
import org.araymond.vigenere.cracker.KeyLength;
import org.araymond.vigenere.cracker.KeyLengthEstimator;
import org.araymond.vigenere.cracker.friedman.FriendmanKeyLengthEstimator;
import org.araymond.vigenere.cracker.kasiki.KasikiKeyLengthEstimator;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * Hello world!
 */
public class App {
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
            default:
                err.println("Type 'help' for vigenere usage.");
                break;
        }
        System.exit(0);
    }

    private static void help() {
        out.println("Usage: " + System.getProperty("line.separator") +
                "   - cypher <plaintText> <key>     : Encode a text using vigenere algorithm" + System.getProperty("line.separator") +
                "   - uncypher <encodedText> <key>  : Decode a text using vigenere algorithm" + System.getProperty("line.separator") +
                "   - estimate <encodedText>        : Estimate key lengths using Babbage and Kasiski method and Friendman test."
        );
    }

    private static void cyphering(final String[] args) {
        if (args.length != 3) {
            err.println("Cyphering syntax: cypher <plaintText> <key>");
            System.exit(1);
        }
        final VigenereCipher vigenere = new VigenereCipher();
        out.println(vigenere.cypher(args[1], args[2]));
    }

    private static void uncyphering(final String[] args) {
        if (args.length != 3) {
            err.println("Uncyphering syntax: uncypher <encodedText> <key>");
            System.exit(1);
        }
        final VigenereCipher vigenere = new VigenereCipher();
        out.println(vigenere.uncypher(args[1], args[2]));
    }

    private static void estimate(final String[] args) {
        if (args.length != 2) {
            err.println("Estimating syntax syntax: estimate <encodedText>");
            System.exit(1);
        }
        final KeyLengthEstimator kasikiEstimator = new KasikiKeyLengthEstimator();

        out.println("Possibles key lengths are :");
        out.println("    For Babbage and kasiki:");
        final List<KeyLength> kasiskiKeys = kasikiEstimator.estimate(args[1]);
        Collections.sort(kasiskiKeys, Comparator.comparingInt(KeyLength::getOccurence).reversed());
        for (final KeyLength keyLength : kasiskiKeys) {
            final String toPrint = String.format("        - Key length: %2s  Occurences: %s", keyLength.getLength(), keyLength.getOccurence());
            out.println(toPrint);
        }

        final KeyLengthEstimator friedmanEstimator = new FriendmanKeyLengthEstimator();
        out.println("    For Friedman test:");
        out.println("        - Key length : " + friedmanEstimator.estimate(args[1]).get(0).getLength());
    }

}
