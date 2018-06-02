package com.reactive.owm.domain.database;

import com.reactive.owm.domain.Domain;

import org.junit.Test;

import io.reactivex.Maybe;

import static org.junit.Assert.assertTrue;

public class FavoriteCityIdsQueryTest {

    @Test
    public void applyThenReturnStreamWithFavoriteCityIdsTable() {

        boolean result = Maybe.just(new Domain(Maybe.just(new MockDatabaseGateway()), Maybe.empty()))
                .compose(new FavoriteCityIdsQuery())
                .map(ignoredTable -> true)
                .defaultIfEmpty(false)
                .blockingGet();

        assertTrue(result);
    }


}