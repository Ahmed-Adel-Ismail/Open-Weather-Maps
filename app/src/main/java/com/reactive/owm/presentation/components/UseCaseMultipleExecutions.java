package com.reactive.owm.presentation.components;

import android.support.annotation.NonNull;

import io.reactivex.subjects.BehaviorSubject;

public class UseCaseMultipleExecutions<T> extends UseCaseExecution<T> {

    public UseCaseMultipleExecutions(@NonNull BehaviorSubject<Boolean> progress,
                                     @NonNull BehaviorSubject<T> dataSource) {

        super(progress, dataSource, source -> true);
    }
}