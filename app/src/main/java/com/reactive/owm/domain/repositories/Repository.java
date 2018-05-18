package com.reactive.owm.domain.repositories;

import com.reactive.owm.domain.Domain;
import com.reactive.owm.domain.database.DatabaseGateway;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Scheduler;

class Repository {

    final Maybe<Domain> domain;
    final Scheduler scheduler;

    Repository(Maybe<Domain> domain, Scheduler scheduler) {
        this.domain = domain;
        this.scheduler = scheduler;
    }

}
