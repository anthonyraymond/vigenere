package org.araymond.vigenere.cracker;

import com.google.common.base.MoreObjects;

/**
 * Created by raymo on 22/11/2016.
 */
public class KeyLength {

    private final int length;
    private final int occurence;

    public KeyLength(final int length, final int occurence) {
        this.length = length;
        this.occurence = occurence;
    }

    public int getLength() {
        return length;
    }

    public int getOccurence() {
        return occurence;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final KeyLength that = (KeyLength) other;
        return length == that.length &&
                occurence == that.occurence;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("length", length)
                .add("occurence", occurence)
                .toString();
    }
}
