package com.reactive.owm.presentation.components;

import android.support.annotation.NonNull;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;

class UseCaseExecution<T> implements FlowableTransformer<T, Disposable> {


    private final BehaviorSubject<Boolean> progress;
    private final BehaviorSubject<T> dataSource;
    private final Predicate<BehaviorSubject<T>> preExecuteCondition;

    UseCaseExecution(@NonNull BehaviorSubject<Boolean> progress,
                     @NonNull BehaviorSubject<T> dataSource,
                     @NonNull Predicate<BehaviorSubject<T>> preExecuteCondition) {

        this.progress = progress;
        this.dataSource = dataSource;
        this.preExecuteCondition = preExecuteCondition;
    }

    @Override
    public Publisher<Disposable> apply(Flowable<T> upstream) {
        try {
            return invokeApply(upstream);
        } catch (Exception e) {
            return Flowable.error(e);
        }
    }

    private Flowable<Disposable> invokeApply(Flowable<T> upstream) throws Exception {
        return !progress.getValue() && preExecuteCondition.test(dataSource)
                ? Flowable.just(executeUseCase(upstream, progress, dataSource))
                : Flowable.empty();
    }

    private Disposable executeUseCase(Flowable<T> upstream,
                                      BehaviorSubject<Boolean> progressing,
                                      BehaviorSubject<T> dataSource) {

        progressing.onNext(true);
        return upstream
                .doFinally(() -> progressing.onNext(false))
                .doOnNext(result -> System.out.println("use-case result : " + result))
                .subscribe(dataSource::onNext, Throwable::printStackTrace);
    }
}