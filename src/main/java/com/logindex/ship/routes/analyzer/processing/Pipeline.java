package com.logindex.ship.routes.analyzer.processing;

import java.util.function.Function;

/**
 * @author Rafał Bajek
 */
interface Pipeline<T, R> extends Function<T, R> {

    R process(T request);

    @Override
    default R apply(T t) {
        return process(t);
    }
}
