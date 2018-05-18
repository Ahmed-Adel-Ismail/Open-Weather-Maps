package com.reactive.owm.domain;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.reactive.owm.App;
import com.reactive.owm.domain.database.DatabaseGateway;
import com.reactive.owm.domain.database.DatabaseGatewayInitializer;
import com.reactive.owm.domain.server.ServerGateway;
import com.reactive.owm.domain.server.ServerGatewayInitializer;

import io.reactivex.subjects.SingleSubject;

@SuppressLint("CheckResult")
public class Domain {


    private final SingleSubject<DatabaseGateway> database = SingleSubject.create();
    private final SingleSubject<ServerGateway> server = SingleSubject.create();


    public Domain(App app) {

        new ServerGatewayInitializer()
                .apply(app)
                .subscribe(server);

        new DatabaseGatewayInitializer()
                .apply(app)
                .subscribe(database);
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    public Domain() {
    }


    public SingleSubject<DatabaseGateway> getDatabase() {
        return database;
    }

    public SingleSubject<ServerGateway> getServer() {
        return server;
    }
}
