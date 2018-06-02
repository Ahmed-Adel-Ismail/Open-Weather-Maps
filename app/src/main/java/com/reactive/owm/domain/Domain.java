package com.reactive.owm.domain;

import com.reactive.owm.domain.database.DatabaseGateway;
import com.reactive.owm.domain.server.ServerGateway;

import io.reactivex.Maybe;

public class Domain {


    private final Maybe<DatabaseGateway> database;
    private final Maybe<ServerGateway> server;

    public Domain(Maybe<DatabaseGateway> database, Maybe<ServerGateway> server) {
        this.database = database;
        this.server = server;
    }

    public Maybe<DatabaseGateway> getDatabase() {
        return database;
    }

    public Maybe<ServerGateway> getServer() {
        return server;
    }
}
