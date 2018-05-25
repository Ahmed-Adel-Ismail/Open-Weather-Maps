package com.reactive.owm.presentation.components;

import android.support.annotation.NonNull;

import io.reactivex.subjects.BehaviorSubject;

public class UseCaseSingleExecution<T> extends UseCaseExecution<T> {

    public UseCaseSingleExecution(@NonNull BehaviorSubject<Boolean> progress,
                                  @NonNull BehaviorSubject<T> dataSource) {

        super(progress, dataSource, source -> source.getValue() == null);
    }

}
