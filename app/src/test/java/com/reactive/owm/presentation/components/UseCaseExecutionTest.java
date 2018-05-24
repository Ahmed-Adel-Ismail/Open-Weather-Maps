package com.reactive.owm.presentation.components;

import com.reactive.owm.presentation.components.UseCaseExecution;

import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.subjects.BehaviorSubject;

import static org.junit.Assert.*;

public class UseCaseExecutionTest {

    @Test
    public void applyWithProgressingAsTrueThenDoNotUpdateCitiesCount() {

        BehaviorSubject<Boolean> progressing = BehaviorSubject.createDefault(true);
        BehaviorSubject<Integer> citiesCount = BehaviorSubject.create();

        new UseCaseExecution<>(progressing, citiesCount).apply(Flowable.just(10));

        assertTrue(citiesCount.getValue() == null);


    }

    @Test
    public void applyWithProgressingAsFalseThenUpdateCitiesCount() {

        BehaviorSubject<Boolean> progressing = BehaviorSubject.createDefault(false);
        BehaviorSubject<Integer> citiesCount = BehaviorSubject.create();

        new UseCaseExecution<>(progressing, citiesCount).apply(Flowable.just(10));

        assertEquals(Integer.valueOf(10), citiesCount.getValue());

    }

    @Test
    public void applyWithProgressingAsFalseThenStopProgressOnFinish() {

        BehaviorSubject<Boolean> progressing = BehaviorSubject.createDefault(false);
        BehaviorSubject<Integer> citiesCount = BehaviorSubject.create();

        new UseCaseExecution<>(progressing, citiesCount).apply(Flowable.just(10));

        assertFalse(progressing.getValue());


    }

}