package com.reactive.owm.domain.components;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class TextInputValidation implements ObservableTransformer<String,String> {

    @Override
    public ObservableSource<String> apply(Observable<String> upstream) {
        return upstream
                .map(String::trim)
                .filter(text -> !text.isEmpty())
                .filter(text -> !"".equals(text));
    }
}
