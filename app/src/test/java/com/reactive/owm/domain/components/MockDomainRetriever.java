package com.reactive.owm.domain.components;

import com.reactive.owm.domain.Domain;

import java.util.concurrent.Callable;

import io.reactivex.Maybe;

public class MockDomainRetriever implements Callable<Maybe<Domain>> {

    private final Domain domain;

    public MockDomainRetriever(Domain domain) {
        this.domain = domain;
    }

    @Override
    public Maybe<Domain> call() {
        return Maybe.just(domain);
    }
}