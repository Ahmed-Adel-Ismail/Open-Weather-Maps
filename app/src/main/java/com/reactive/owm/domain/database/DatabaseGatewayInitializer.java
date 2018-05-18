package com.reactive.owm.domain.database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.reactive.owm.App;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class DatabaseGatewayInitializer implements Function<Context, Single<DatabaseGateway>> {

    private static final String DATABASE_NAME = "DatabaseGateway.db";

    @Override
    public Single<DatabaseGateway> apply(Context context) {
        return Single.defer(() -> buildDatabase(context));
    }

    private Single<DatabaseGateway> buildDatabase(Context context) {
        return Single.just(Room.databaseBuilder(context, DatabaseGateway.class, DATABASE_NAME))
                .map(RoomDatabase.Builder::build);
    }
}
