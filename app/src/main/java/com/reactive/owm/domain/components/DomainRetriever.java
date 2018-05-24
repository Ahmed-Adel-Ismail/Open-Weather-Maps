package com.reactive.owm.domain.components;

import android.app.Application;
import android.support.annotation.NonNull;

import com.reactive.owm.App;
import com.reactive.owm.domain.Domain;

import io.reactivex.Maybe;
import io.reactivex.functions.Function;

public class DomainRetriever implements Function<Application, Maybe<Domain>> {

    @Override
    public Maybe<Domain> apply(@NonNull Application application) {
        return Maybe.just(application)
                .cast(App.class)
                .flatMap(App::getDomain)
                .onErrorComplete();
    }
}
