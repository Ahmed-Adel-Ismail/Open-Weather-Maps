package com.reactive.owm.domain.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.support.annotation.NonNull;

public class MockDatabaseGateway extends DatabaseGateway {

    @Override
    public CitiesTable getCitiesTable() {
        return new MockCitiesTable();
    }

    @Override
    public FavoriteCityIdsTable getFavoriteCityIdsTable() {
        return new MockFavoriteCityIdsTable();
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}