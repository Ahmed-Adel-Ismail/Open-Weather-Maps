package com.reactive.owm.domain.database;

import android.annotation.SuppressLint;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.reactive.owm.App;
import com.reactive.owm.domain.Domain;
import com.reactive.owm.domain.components.CitiesUpdaterFromFile;
import com.reactive.owm.entities.City;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DatabaseGatewayInitializer implements Function<Context, Single<DatabaseGateway>> {

    private static final String DATABASE_NAME = "DatabaseGateway.db";

    @Override
    public Single<DatabaseGateway> apply(Context context) {
        return Single.defer(() -> buildDatabase(context));
    }

    private Single<DatabaseGateway> buildDatabase(Context context) {
        return Single.just(Room.databaseBuilder(context, DatabaseGateway.class, DATABASE_NAME))
                .doOnSuccess(builder -> builder.addCallback(updateCitiesFromFile()))
                .map(RoomDatabase.Builder::build);
    }

    @NonNull
    private RoomDatabase.Callback updateCitiesFromFile() {
        return new RoomDatabase.Callback() {
            @SuppressLint("CheckResult")
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                App.getInstance()
                        .subscribeOn(Schedulers.single())
                        .observeOn(Schedulers.single())
                        .flatMap(App::getDomain)
                        .flatMap(Domain::getDatabase)
                        .map(DatabaseGateway::getCitiesTable)
                        .map(citiesTable -> (Consumer<City>) citiesTable::insert)
                        .subscribe(new CitiesUpdaterFromFile(), Throwable::printStackTrace);
            }
        };
    }


}
