package com.reactive.owm.domain.database;

import com.reactive.owm.domain.Domain;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.schedulers.Schedulers;

public class FavoriteCityIdsQuery implements MaybeTransformer<Domain, FavoriteCityIdsTable> {

    @Override
    public MaybeSource<FavoriteCityIdsTable> apply(Maybe<Domain> upstream) {
        return upstream.observeOn(Schedulers.io())
                .flatMap(Domain::getDatabase)
                .map(DatabaseGateway::getFavoriteCityIdsTable);
    }


}
