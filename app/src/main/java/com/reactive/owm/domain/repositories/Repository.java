package com.reactive.owm.domain.repositories;

import com.reactive.owm.domain.Domain;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class Repository {

    private final Maybe<Domain> domain;

    protected Repository(Maybe<Domain> domain) {
        this.domain = domain;
    }
}
