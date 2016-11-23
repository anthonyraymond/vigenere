package org.araymond.vigenere.cracker.utils;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by raymo on 23/11/2016.
 */
public class CommonDivisors {

    /**
     * Find all common divisors between all matched positions (duplicated are kepts and returned).
     * Would never return a divisor strictly greater than {@link CommonDivisorLimit#getLimit()}.
     *
     * @return all common divisors between all matched positions (duplicated are kepts and returned).
     */
    public List<Integer> findFor(final Iterable<Integer> values, final CommonDivisorLimit maxDivisor) {
        final List<Integer> sortedList = Lists.newArrayList(values);
        Collections.sort(sortedList);
        final List<Integer> divisors = new ArrayList<>();
        // Basicaly, we compare a list element to all the followings elements.
        //  ie: with [1, 2, 3] we will compare 1 to 2, 1 to 3 and 2 to 3
        for (int i = 0; i < sortedList.size() - 1; ++i) {
            for (int j = i + 1; j < sortedList.size(); ++j) {
                // We add the divisors found from the pair to the global list.
                divisors.addAll(
                        this.findFor(sortedList.get(i), sortedList.get(j), maxDivisor)
                );
            }
        }

        return divisors;
    }

    /**
     * Find all the commons divisors between two numbers (1 is excluded).
     * Would never return a divisor strictly greater than {@link CommonDivisorLimit#getLimit()}.
     *
     * @param first     first number
     * @param second    second number
     * @return all the common divisors between first and second (1 excluded)
     */
    public Collection<Integer> findFor(final int first, final int second, final CommonDivisorLimit maxDivisor) {
        final Collection<Integer> commons = new ArrayList<>();
        final int min = Math.min(first, second);

        // Divisor 1 is excluded, start at two
        for(int i = 2; i <= min / 2 && i <= maxDivisor.getLimit(); i++) {
            if (first % i == 0 && second % i == 0) {
                commons.add(i);
            }
        }

        if (first % min == 0 && second % min == 0 && min <= maxDivisor.getLimit()) {
            commons.add(min);
        }
        return commons;
    }

    public static final class CommonDivisorLimit {

        private final Integer limit;

        public CommonDivisorLimit(final Integer limit) {
            this.limit = limit;
        }

        public Integer getLimit() {
            return limit;
        }
    }

}
