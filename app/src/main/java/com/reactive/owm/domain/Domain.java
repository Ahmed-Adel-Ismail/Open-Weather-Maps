package com.reactive.owm.domain;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.reactive.owm.App;
import com.reactive.owm.domain.database.DatabaseGateway;
import com.reactive.owm.domain.database.DatabaseGatewayInitializer;
import com.reactive.owm.domain.server.ServerGateway;
import com.reactive.owm.domain.server.ServerGatewayInitializer;

import io.reactivex.Maybe;
import io.reactivex.subjects.MaybeSubject;
import io.reactivex.subjects.SingleSubject;

@SuppressLint("CheckResult")
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
