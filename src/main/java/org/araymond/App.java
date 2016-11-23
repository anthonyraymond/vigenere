package org.araymond;

import org.araymond.vigenere.VigenereCipher;
import org.araymond.vigenere.cracker.KeyLength;
import org.araymond.vigenere.cracker.kasiki.KasikiKeyLengthEstimator;

/**
 * Hello world!
 */
public class App {
    public static void main(final String[] args) {
        if (args.length < 1) {
            System.err.println("Type 'help' for vigenere usage.");
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
                System.err.println("Type 'help' for vigenere usage.");
                break;
        }
        System.exit(0);
    }

    public static void help() {
        System.out.println("Usage: " + System.getProperty("line.separator") +
                "   - cypher <plaintText> <key>     : Encode a text using vigenere algorithm" + System.getProperty("line.separator") +
                "   - uncypher <encodedText> <key>  : Decode a text using vigenere algorithm" + System.getProperty("line.separator") +
                "   - estimate <encodedText>        : Estimate key lengths using babbage and kasiki method."
        );
    }

    public static void cyphering(final String[] args) {
        if (args.length != 3) {
            System.err.println("Cyphering syntax: cypher <plaintText> <key>");
            System.exit(1);
        }
        final VigenereCipher vigenere = new VigenereCipher();
        System.out.println(vigenere.cypher(args[1], args[2]));
    }

    public static void uncyphering(final String[] args) {
        if (args.length != 3) {
            System.err.println("Uncyphering syntax: uncypher <encodedText> <key>");
            System.exit(1);
        }
        final VigenereCipher vigenere = new VigenereCipher();
        System.out.println(vigenere.uncypher(args[1], args[2]));
    }

    public static void estimate(final String[] args) {
        if (args.length != 2) {
            System.err.println("Estimating syntax syntax: estimate <encodedText>");
            System.exit(1);
        }
        final KasikiKeyLengthEstimator estimator = new KasikiKeyLengthEstimator();

        System.out.println("Possibles key lengths are :");
        for (final KeyLength keyLength : estimator.estimate(args[1])) {
            System.out.println("    - Key length : " + keyLength.getLength() + "  Occurences :" + keyLength.getOccurence());
        }
    }

}
