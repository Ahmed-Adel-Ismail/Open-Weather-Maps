package com.reactive.owm.domain.components;

import android.app.Application;
import android.support.annotation.NonNull;

import com.reactive.owm.App;
import com.reactive.owm.domain.Domain;

import java.util.concurrent.Callable;

import io.reactivex.Maybe;
import io.reactivex.functions.Function;

public class DomainRetriever implements Callable<Maybe<Domain>> {

    private final Application application;

    public DomainRetriever(@NonNull Application application) {
        this.application = application;
    }

    @Override
    public Maybe<Domain> call() {
        return Maybe.just(application)
                .cast(App.class)
                .flatMap(App::getDomain)
                .onErrorComplete();
    }
}
