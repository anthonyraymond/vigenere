package org.araymond.vigenere.cracker.kasiski;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by raymo on 22/11/2016.
 */
public class Repetition {

    private final String string;
    private final List<Integer> distances;

    Repetition(final String string, final List<Integer> positions) {
        this.string = string;
        this.distances = new ArrayList<>();

        for (int i = 0; i < positions.size() - 1; ++i) {
            for (int j = i + 1; j < positions.size(); ++j) {
                // compute distances from positions
                distances.add(Math.abs(positions.get(j) - positions.get(i)));
            }
        }
    }

    List<Integer> getDistances() {
        return Collections.unmodifiableList(this.distances);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final Repetition that = (Repetition) other;
        return Objects.equal(string, that.string);
    }

}
