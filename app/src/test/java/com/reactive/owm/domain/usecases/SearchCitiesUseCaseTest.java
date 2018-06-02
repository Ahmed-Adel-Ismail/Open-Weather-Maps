package com.reactive.owm.domain.usecases;

import com.reactive.owm.domain.Domain;
import com.reactive.owm.domain.components.MockDomainRetriever;
import com.reactive.owm.domain.components.ValidTextInputFilter;
import com.reactive.owm.domain.database.CitiesQuery;
import com.reactive.owm.domain.database.MockCitiesTable;
import com.reactive.owm.domain.database.MockDatabaseGateway;
import com.reactive.owm.entities.City;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Maybe;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchCitiesUseCaseTest {

    @Test
    public void applyWithEmptyStringThenReturnEmptyList() throws Exception {

        final List<City> result = new ArrayList<>();
        TestScheduler scheduler = new TestScheduler();

        searchCitiesUseCase(scheduler)
                .apply("")
                .subscribe(result::addAll);

        scheduler.triggerActions();

        assertTrue(result.isEmpty());

    }

    @Test
    public void applyWithInvalidStringThenReturnEmptyList() throws Exception {

        final List<City> result = new ArrayList<>();
        TestScheduler scheduler = new TestScheduler();

        searchCitiesUseCase(scheduler)
                .apply(" ")
                .subscribe(result::addAll);

        scheduler.triggerActions();

        assertTrue(result.isEmpty());

    }

    @Test
    public void applyWithNotMatchingStringThenReturnEmptyList() throws Exception {

        final List<City> result = new ArrayList<>();
        TestScheduler scheduler = new TestScheduler();

        searchCitiesUseCase(scheduler)
                .apply("iiiiiiii")
                .subscribe(result::addAll);

        scheduler.triggerActions();

        assertTrue(result.isEmpty());

    }

    private SearchCitiesUseCase searchCitiesUseCase(TestScheduler scheduler) {
        Domain domain = new Domain(Maybe.just(new MockDatabaseGateway()), Maybe.empty());
        Callable<Maybe<Domain>> domainRetriever = new MockDomainRetriever(domain);
        return new SearchCitiesUseCase(domainRetriever, new ValidTextInputFilter(), new CitiesQuery(scheduler));
    }


    @Test
    public void applyWithValidStringThenReturnListOfMatchedCities() throws Exception {

        final List<City> result = new ArrayList<>();
        TestScheduler scheduler = new TestScheduler();

        searchCitiesUseCase(scheduler)
                .apply("o")
                .doOnNext(System.out::println)
                .subscribe(result::addAll);

        scheduler.triggerActions();

        assertEquals(MockCitiesTable.CITIES_COUNT_WITH_LETTER_O, result.size());

    }


}