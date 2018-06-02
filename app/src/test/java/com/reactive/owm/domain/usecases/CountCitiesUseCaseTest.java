package com.reactive.owm.domain.usecases;

import com.reactive.owm.domain.Domain;
import com.reactive.owm.domain.components.DomainRetriever;
import com.reactive.owm.domain.components.MockDomainRetriever;
import com.reactive.owm.domain.database.CitiesQuery;
import com.reactive.owm.domain.database.MockCitiesTable;
import com.reactive.owm.domain.database.MockDatabaseGateway;

import org.junit.Test;

import java.util.concurrent.Callable;

import io.reactivex.Maybe;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;

public class CountCitiesUseCaseTest {

    @Test
    public void callThenReturnCitiesCount() throws Exception {

        final int[] result = {0};
        TestScheduler scheduler = new TestScheduler();
        Domain domain = new Domain(Maybe.just(new MockDatabaseGateway()), Maybe.empty());
        Callable<Maybe<Domain>> domainRetriever = new MockDomainRetriever(domain);


        new CountCitiesUseCase(domainRetriever, new CitiesQuery(scheduler))
                .call()
                .subscribe(value -> result[0] = value);

        scheduler.triggerActions();

        assertEquals(MockCitiesTable.CITIES_COUNT, result[0]);

    }
}