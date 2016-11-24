package org.araymond.vigenere.cracker;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by raymo on 22/11/2016.
 */
public class KeyLength {

    private final Integer length;
    private Integer occurrence;

    public KeyLength(final Integer length, final Integer occurrence) {
        this.length = length;
        this.occurrence = occurrence;
    }

    public int getLength() {
        return length;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(final Integer occurrence) {
        this.occurrence = occurrence;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final KeyLength keyLength = (KeyLength) other;
        return Objects.equal(length, keyLength.length) &&
                Objects.equal(occurrence, keyLength.occurrence);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(length, occurrence);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("length", length)
                .add("occurrence", occurrence)
                .toString();
    }
}
