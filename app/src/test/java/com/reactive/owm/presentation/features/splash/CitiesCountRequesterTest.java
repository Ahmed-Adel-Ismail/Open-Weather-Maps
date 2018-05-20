package com.reactive.owm.presentation.features.splash;

import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CitiesCountRequesterTest {


    @Test
    public void applyWithProgressingAsTrueThenDoNotUpdateCitiesCount() {

        BehaviorSubject<Boolean> progressing = BehaviorSubject.createDefault(true);
        BehaviorSubject<Integer> citiesCount = BehaviorSubject.create();

        new CitiesCountRequester() {
            @Override
            Single<Integer> queryCitiesCount() {
                return Single.just(10);
            }
        }.apply(progressing, citiesCount);

        assertTrue(citiesCount.getValue() == null);


    }

    @Test
    public void applyWithProgressingAsFalseThenUpdateCitiesCount() {

        BehaviorSubject<Boolean> progressing = BehaviorSubject.createDefault(false);
        BehaviorSubject<Integer> citiesCount = BehaviorSubject.create();

        new CitiesCountRequester() {
            @Override
            Single<Integer> queryCitiesCount() {
                return Single.just(10);
            }
        }.apply(progressing, citiesCount);

        assertEquals(Integer.valueOf(10), citiesCount.getValue());

    }

    @Test
    public void applyWithProgressingAsFalseThenStopProgressOnFinish() {

        BehaviorSubject<Boolean> progressing = BehaviorSubject.createDefault(false);
        BehaviorSubject<Integer> citiesCount = BehaviorSubject.create();

        new CitiesCountRequester() {
            @Override
            Single<Integer> queryCitiesCount() {
                return Single.just(10);
            }
        }.apply(progressing, citiesCount);

        assertFalse(progressing.getValue());


    }

}