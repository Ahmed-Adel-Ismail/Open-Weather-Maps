package com.reactive.owm.domain.components;

import org.junit.Test;

import java.util.NoSuchElementException;

import io.reactivex.Observable;

import static org.junit.Assert.*;

public class ValidTextInputFilterTest {

    @Test(expected = NoSuchElementException.class)
    public void applyWithEmptyTextThenReturnEmptyStream() throws Exception {
        Observable.just("")
                .compose(new ValidTextInputFilter())
                .blockingFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void applyWithInvalidTextThenReturnEmptyStream() throws Exception {
        Observable.just(" ")
                .compose(new ValidTextInputFilter())
                .blockingFirst();
    }

    @Test
    public void applyWithValidTextThenReturnThisTextInTheStream() throws Exception {
        String result = Observable.just("test")
                .compose(new ValidTextInputFilter())
                .blockingFirst();

        assertEquals("test", result);
    }

    @Test
    public void applyWithValidUntrimmedTextThenReturnThisTextTrimmedInTheStream() throws Exception {
        String result = Observable.just(" test ")
                .compose(new ValidTextInputFilter())
                .blockingFirst();

        assertEquals("test", result);
    }

}