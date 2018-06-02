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
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertTrue;

public class CitiesQueryTest {

    @Test
    public void applyThenReturnStreamWithCitiesTable() {

        boolean result = Maybe.just(new Domain(Maybe.just(new MockDatabaseGateway()), Maybe.empty()))
                .compose(new CitiesQuery())
                .map(ignoredTable -> true)
                .defaultIfEmpty(false)
                .blockingGet();


        assertTrue(result);
    }


}