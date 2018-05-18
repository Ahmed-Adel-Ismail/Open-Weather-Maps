package com.reactive.owm.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import io.reactivex.Maybe;

@Entity
public class FavoriteCityId {

    @PrimaryKey
    @NonNull
    public final Long id;

    public FavoriteCityId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public Maybe<Long> getId() {
        return Maybe.just(id);
    }
}
