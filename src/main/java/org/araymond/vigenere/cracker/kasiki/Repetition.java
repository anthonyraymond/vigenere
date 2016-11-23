package org.araymond.vigenere.cracker.kasiki;

import com.google.common.base.Objects;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by raymo on 22/11/2016.
 */
public class Repetition {

    private final String string;
    private final List<Integer> positions;
    private final List<Integer> distances;
    private List<Integer> divisors;

    Repetition(final String string, final List<Integer> positions) {
        this.string = string;
        this.positions = positions;
        Collections.sort(positions);
        this.distances = new ArrayList<>();

        for (int i = 0; i < positions.size() - 1; ++i) {
            for (int j = i + 1; j < positions.size(); ++j) {
                distances.add(positions.get(j) - positions.get(i));
            }
        }
    }

    public List<Integer> getDistances() {
        return Collections.unmodifiableList(this.distances);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final Repetition that = (Repetition) other;
        return Objects.equal(string, that.string) &&
                Objects.equal(positions, that.positions);
    }

}
