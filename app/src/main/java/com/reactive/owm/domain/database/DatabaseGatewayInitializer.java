package com.reactive.owm.domain.database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.reactive.owm.App;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class DatabaseGatewayInitializer implements Function<App, Single<DatabaseGateway>> {

    private static final String DATABASE_NAME = "DatabaseGateway.db";

    @Override
    public Single<DatabaseGateway> apply(App app) {
        return Single.defer(() -> buildDatabase(app));
    }

    private Single<DatabaseGateway> buildDatabase(App app) {
        return Single.just(Room.databaseBuilder(app, DatabaseGateway.class, DATABASE_NAME))
                .map(RoomDatabase.Builder::build);
    }
}
