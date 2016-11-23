package org.araymond.vigenere.cracker;

import java.util.List;

/**
 * Created by raymo on 23/11/2016.
 */
public interface KeyLengthEstimator {

    List<KeyLength> estimate(final String encoded);
}
