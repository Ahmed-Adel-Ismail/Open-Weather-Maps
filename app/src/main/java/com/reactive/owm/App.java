package com.reactive.owm;

import android.app.Application;
import android.support.annotation.NonNull;

import com.reactive.owm.domain.Domain;
import com.reactive.owm.domain.database.DatabaseGateway;
import com.reactive.owm.domain.database.DatabaseGatewayInitializer;
import com.reactive.owm.domain.server.ServerGateway;
import com.reactive.owm.domain.server.ServerGatewayInitializer;

import java.lang.ref.WeakReference;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.MaybeSubject;
import io.reactivex.subjects.SingleSubject;
import io.reactivex.subjects.Subject;

public class App extends Application {

    private static WeakReference<App> instance;
    private final MaybeSubject<Domain> domain = MaybeSubject.create();

    public static Maybe<App> getInstance() {
        return Single.just(instance)
                .filter(reference -> reference.get() != null)
                .map(WeakReference::get);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = new WeakReference<>(this);
        domain.onSuccess(new Domain(database(), server()));
    }


    private Maybe<DatabaseGateway> database() {
        return new DatabaseGatewayInitializer()
                .apply(this)
                .toMaybe();
    }

    private Maybe<ServerGateway> server() {
        return new ServerGatewayInitializer()
                .apply(this)
                .toMaybe();
    }

    public Maybe<Domain> getDomain() {
        return domain;
    }


}
