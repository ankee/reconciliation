package com.sample.problem1.util;

import com.google.common.base.Function;
import com.sample.problem1.core.Identifiable;

/**
 * Created by aagarwal1 on 2/28/2015.
 */
public class Functions {

    private enum IDENTIFIABLE_TO_ID implements Function<Identifiable, Integer> {
        INSTANCE  {
            @Override
            public Integer apply(Identifiable input) {
                return input.getId();
            }
        };
    }

    public static Function<Identifiable, Integer> identifiableToId() {
        return IDENTIFIABLE_TO_ID.INSTANCE;
    }
}
