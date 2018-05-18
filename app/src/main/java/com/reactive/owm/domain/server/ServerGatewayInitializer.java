package com.reactive.owm.domain.server;

import com.reactive.owm.App;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class ServerGatewayInitializer implements Function<App,Single<ServerGateway>> {

    @Override
    public Single<ServerGateway> apply(App app) {
        return null;
    }
}
