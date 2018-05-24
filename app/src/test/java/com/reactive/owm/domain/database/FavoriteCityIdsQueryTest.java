package com.reactive.owm.domain.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.support.annotation.NonNull;

import com.reactive.owm.domain.Domain;
import com.reactive.owm.entities.FavoriteCityId;

import junit.framework.Assert;

import org.junit.Test;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public class FavoriteCityIdsQueryTest {

    @Test
    public void applyThenReturnStreamWithFavoriteCityIdsTable() {

        DatabaseGateway databaseGateway = databaseGatewayWithFavoriteCityIdsTable();

        Maybe.just(new Domain(Maybe.just(databaseGateway), Maybe.empty()))
                .compose(new FavoriteCityIdsQuery())
                .map(ignoredTable -> true)
                .defaultIfEmpty(false)
                .subscribe(Assert::assertTrue);
    }

    @NonNull
    private DatabaseGateway databaseGatewayWithFavoriteCityIdsTable() {
        return new DatabaseGateway() {
            @Override
            public CitiesTable getCitiesTable() {
                return null;
            }

            @Override
            public FavoriteCityIdsTable getFavoriteCityIdsTable() {
                return new FavoriteCityIdsTable() {
                    @Override
                    public Flowable<List<FavoriteCityId>> queryFavoriteCityIds() {
                        return null;
                    }

                    @Override
                    public void insertFavoriteCityId(FavoriteCityId favoriteCityId) {

                    }
                };
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
        };
    }

}