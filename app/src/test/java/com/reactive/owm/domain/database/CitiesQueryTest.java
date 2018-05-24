package com.reactive.owm.domain.database;

import android.arch.paging.DataSource;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.support.annotation.NonNull;

import com.reactive.owm.domain.Domain;
import com.reactive.owm.entities.City;

import junit.framework.Assert;

import org.junit.Test;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class CitiesQueryTest {

    @Test
    public void applyThenReturnStreamWithCitiesTable() {

        DatabaseGateway databaseGateway = databaseGatewayWithCitiesTable();

        Maybe.just(new Domain(Maybe.just(databaseGateway), Maybe.empty()))
                .compose(new CitiesQuery())
                .map(ignoredTable -> true)
                .defaultIfEmpty(false)
                .subscribe(Assert::assertTrue);
    }

    @NonNull
    private DatabaseGateway databaseGatewayWithCitiesTable() {
        return new DatabaseGateway() {
            @Override
            public CitiesTable getCitiesTable() {
                return new CitiesTable() {
                    @Override
                    public Flowable<List<City>> queryCityByName(String fuzzyName) {
                        return null;
                    }

                    @Override
                    public DataSource.Factory<Integer, City> queryAllCities() {
                        return null;
                    }

                    @Override
                    public Single<Integer> queryCitiesCount() {
                        return null;
                    }

                    @Override
                    public Flowable<List<City>> queryCitiesByIds(List<Long> ids) {
                        return null;
                    }
                };
            }

            @Override
            public FavoriteCityIdsTable getFavoriteCityIdsTable() {
                return null;
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