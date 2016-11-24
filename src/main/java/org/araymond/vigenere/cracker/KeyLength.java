package org.araymond.vigenere.cracker;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by raymo on 22/11/2016.
 */
public class KeyLength {

    private final Integer length;
    private Integer occurence;

    public KeyLength(final Integer length, final Integer occurrence) {
        this.length = length;
        this.occurence = occurrence;
    }

    public int getLength() {
        return length;
    }

    public int getOccurence() {
        return occurence;
    }

    public void setOccurence(final Integer occurence) {
        this.occurence = occurence;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final KeyLength keyLength = (KeyLength) other;
        return Objects.equal(length, keyLength.length) &&
                Objects.equal(occurence, keyLength.occurence);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(length, occurence);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("length", length)
                .add("occurence", occurence)
                .toString();
    }
}
