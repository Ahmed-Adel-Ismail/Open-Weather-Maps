package com.reactive.owm.presentation.features.home;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;

public class HomeViewModelTest {


    @Test
    public void updateSearchInputWithValidTextThenNotifyTriggerSearch() {

        TestScheduler scheduler = new TestScheduler();
        HomeViewModel viewModel = new HomeViewModel(scheduler);
        String[] result = {null};

        viewModel.triggerSearch
                .share()
                .subscribe(text -> result[0] = text);

        viewModel.searchInput.onNext("a");

        scheduler.advanceTimeBy(4, TimeUnit.SECONDS);

        assertEquals("a", result[0]);

    }

    @Test
    public void updateSearchInputWithMultipleValidTextsThenNotifyTriggerSearchAfterTheLastOne() {

        TestScheduler scheduler = new TestScheduler();
        HomeViewModel viewModel = new HomeViewModel(scheduler);
        String[] result = {null};

        viewModel.triggerSearch
                .share()
                .subscribe(text -> result[0] = text);

        viewModel.searchInput.onNext("a");
        scheduler.triggerActions();
        if (result[0] != null) throw new AssertionError("value updated earlier than expected");

        viewModel.searchInput.onNext("ab");
        scheduler.triggerActions();
        if (result[0] != null) throw new AssertionError("value updated earlier than expected");

        scheduler.advanceTimeBy(4, TimeUnit.SECONDS);

        assertEquals("ab", result[0]);

    }

}