package org.araymond.vigenere.cracker.kasiki;

import com.google.common.base.Objects;

import java.util.*;

/**
 * Created by raymo on 22/11/2016.
 */
public class Repetition {

    private final String string;
    private final List<Integer> positions;
    private List<Integer> divisors;

    public Repetition(final String string, final List<Integer> positions) {
        this.string = string;
        this.positions = positions;
    }

    /**
     * Find all common divisors between all matched positions (duplicated are kepts and returned).
     *
     * @return all common divisors between all matched positions (duplicated are kepts and returned).
     */
    public List<Integer> getCommonDivisors() {
        // Since divisors is pretty long to load, we use lazy loading.
        if (divisors != null) {
            return Collections.unmodifiableList(divisors);
        }

        divisors = new ArrayList<>();
        // Basicaly, we compare a list element to all the followings elements.
        //  ie: with [1, 2, 3] we will compare 1 to 2, 1 to 3 and 2 to 3
        for (int i = 0; i < positions.size() - 1; ++i) {
            for (int j = i + 1; j < positions.size(); ++j) {
                // We add the divisors found from the pair to the global list.
                divisors.addAll(computeCommonDivisors(positions.get(i), positions.get(j)));
            }
        }

        return divisors;
    }

    /**
     * Find all the commons divisors between two numbers (1 is excluded)
     *
     * @param first     first number
     * @param second    second number
     * @return all the common divisors between first and second (1 excluded)
     */
    private Collection<Integer> computeCommonDivisors(final int first, final int second) {
        final Collection<Integer> commons = new ArrayList<>();
        final int min = Math.min(first, second);

        // Divisor 1 is excluded, start at two
        for(int i = 2; i <= min / 2; i++) {
            if (first % i == 0 && second % i == 0) {
                commons.add(i);
            }
        }

        if (min != 0 && first % min == 0 && second % min == 0) {
            commons.add(min);
        }
        return commons;
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
