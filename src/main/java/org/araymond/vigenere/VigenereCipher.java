package org.araymond.vigenere;

/**
 * Created by raymo on 22/11/2016.
 */
public class VigenereCipher {

    /**
     * Take a string as a parameter and :
     *  - Remove spaces.
     *  - Remove all non alphabetical charaters.
     *  - Lowercase all characters.
     *
     * @param plaintText a String that may contains unwanted chars
     * @return A String without all unwanted characters
     */
    String normalizePlainText(final String plaintText) {
        return plaintText.replaceAll("[^A-Za-z]+", "").toLowerCase();
    }

    /**
     * Take a String as parameter and return the ascii representation of the String (as a int array)
     *
     * @param key String to be converted to ASCII value array
     * @return The int array that represent the String ASCII values
     */
    int[] computeKeyAsNumericalValue(final CharSequence key) {
        return key.chars().toArray();
    }

    /**
     * Take a character as a parameter and the ASCII value of the key character used to encode. Returns the cyphered character.
     *
     * @param original the plaintText character
     * @param gap the ASCII value of the key character
     * @return the cyphered character
     */
    char computeCypherSubstituteChar(final char original, final int gap) {
        return (char) (((original - 'a' + (gap - 'a')) %26) + 'a');
    }

    /**
     * Take a character as a parameter and the ASCII value of the key character used to decode. Returns the uncyphered character.
     *
     * @param original the encoded character
     * @param gap the ASCII value of the key character
     * @return the uncyphered character
     */
    char computeUncypherSubstituteChar(final char original, final int gap) {
        return (char) (Math.floorMod((original - 'a' - (gap - 'a')), 26) + 'a');
    }

    /**
     * Encrypt a text using vigenere algorithm and the provided key
     *
     * @param plaintText text to encrypt
     * @param key to use for encrypt
     * @return encrypted text
     */
    public String cypher(String plaintText, String key) {
        if (plaintText.length() == 0 || key.length() == 0) {
            return plaintText;
        }
        // Remove unwanted char from both plaintText and key
        plaintText = this.normalizePlainText(plaintText);
        key = this.normalizePlainText(key);
        // Get the ascii value of the key's chars, instead of the string
        final int[] keyValue = computeKeyAsNumericalValue(key);

        final StringBuilder sb = new StringBuilder(plaintText.length());
        for (int i = 0; i < plaintText.length(); ++i) {
            // Compute each replacement char and build the output string
            final char cypheredChar = computeCypherSubstituteChar(plaintText.charAt(i), keyValue[i % key.length()]);
            sb.append(cypheredChar);
        }

        return sb.toString();
    }

    /**
     * Decrypt a text using vigenere algorithm and the provided key
     *
     * @param encoded text to decrypt
     * @param key to use for decrypt
     * @return plaintext
     */
    public String uncypher(String encoded, String key) {
        if (encoded.length() == 0 || key.length() == 0) {
            return encoded;
        }
        // Remove unwanted char from both plaintText and key
        encoded = normalizePlainText(encoded);
        key = normalizePlainText(key);

        // Get the ascii value of the key's chars, instead of the string
        final int[] keyValue = computeKeyAsNumericalValue(key);

        final StringBuilder sb = new StringBuilder(encoded.length());
        for (int i = 0; i < encoded.length(); ++i) {
            final char decodedChar = computeUncypherSubstituteChar(encoded.charAt(i), keyValue[i % key.length()]);
            sb.append(decodedChar);
        }

        return sb.toString();
    }

}
