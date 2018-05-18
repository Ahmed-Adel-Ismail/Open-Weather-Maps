package com.reactive.owm.domain.repositories;

import android.annotation.SuppressLint;
import android.support.annotation.RestrictTo;

import com.reactive.owm.App;
import com.reactive.owm.domain.Domain;

import io.reactivex.Maybe;

public class InitializerRepository extends Repository {


    @SuppressLint("RestrictedApi")
    public InitializerRepository() {
        this(App.getInstance().flatMap(App::getDomain));
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    public InitializerRepository(Maybe<Domain> domain) {
        super(domain);
    }
}
