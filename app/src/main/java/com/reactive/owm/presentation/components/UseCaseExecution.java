package com.reactive.owm.presentation.components;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

public class UseCaseExecution<T> implements FlowableTransformer<T, Disposable> {


    private final BehaviorSubject<Boolean> progress;
    private final BehaviorSubject<T> dataSource;

    public UseCaseExecution(BehaviorSubject<Boolean> progress, BehaviorSubject<T> dataSource) {
        this.progress = progress;
        this.dataSource = dataSource;
    }

    @Override
    public Publisher<Disposable> apply(Flowable<T> upstream) {
        return !progress.getValue() && dataSource.getValue() == null
                ? Flowable.just(executeUseCase(upstream, progress, dataSource))
                : Flowable.empty();
    }

    private Disposable executeUseCase(Flowable<T> upstream, BehaviorSubject<Boolean> progressing, BehaviorSubject<T> dataSource) {
        progressing.onNext(true);
        return upstream
                .doFinally(() -> progressing.onNext(false))
                .subscribe(dataSource::onNext, Throwable::printStackTrace);
    }
}
