package org.araymond.vigenere.cracker.utils;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by raymo on 23/11/2016.
 */
public class CommonDivisors {

    private final CommonDivisorLimit maxDivisor;

    public CommonDivisors(final CommonDivisorLimit maxDivisor) {
        this.maxDivisor = maxDivisor;
    }

    /**
     * Find all common divisors between all distances (duplicated are kepts and returned).
     * Would never return a divisor strictly greater than {@link CommonDivisorLimit#getLimit()}.
     *
     * @return all common divisors between all matched positions (duplicated are kepts and returned).
     */
    public Stream<Integer> findFor(final List<Integer> values) {
        if (values.size() == 1) {
            final Stream.Builder<Integer> builder = Stream.builder();
            for (int i = 2; i <= values.get(0) / 2 && i <= maxDivisor.getLimit(); i++) {
                if (values.get(0) % i == 0) {
                    builder.add(i);
                }
            }
            return builder.build();
        }

        final AtomicInteger counterToSkip = new AtomicInteger();
        // Basicaly, we compare a list element to all the followings elements.
        //  ie: with [1, 2, 3] we will compare 1 to 2, 1 to 3 and 2 to 3 (this is why we use counterToSkip, so we can skip already compared numbers)
        return values.stream()
                .flatMap(distance1 ->
                        values.stream()
                                .skip(counterToSkip.incrementAndGet())
                                .flatMap(distance2 -> this.findFor(distance1, distance2).stream())
                );


        // The above code is a mess to understand, when not used to Stream API,
        //   this is how it would looks like if if has been written in plain java
        /*
        final List<Integer> divisors = new ArrayList<>();
        for (int i = 0; i < values.size() - 1; ++i) {
            for (int j = i + 1; j < values.size(); ++j) {
                // We add the divisors found from the pair to the global list.
                divisors.addAll(
                        this.findFor(sortedList.get(i), sortedList.get(j), maxDivisor)
                );
            }
        }

        return divisors;
        */
    }

    /**
     * Find all the commons divisors between two numbers (1 is excluded).
     * Would never return a divisor strictly greater than {@link CommonDivisorLimit#getLimit()}.
     *
     * @param first     first number
     * @param second    second number
     * @return all the common divisors between first and second (1 excluded)
     */
    public Collection<Integer> findFor(final Integer first, final Integer second) {
        final Collection<Integer> commons = new ArrayList<>();
        final Integer min = Math.min(first, second);

        // Divisor 1 is excluded, start at two
        for(Integer i = 2; i <= min / 2 && i <= maxDivisor.getLimit(); i++) {
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
