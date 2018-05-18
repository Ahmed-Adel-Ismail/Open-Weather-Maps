package com.reactive.owm.domain.components;

import android.content.ContextWrapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.reactive.owm.App;
import com.reactive.owm.R;
import com.reactive.owm.entities.City;

import java.io.InputStreamReader;

import io.reactivex.functions.Consumer;

public class CitiesUpdaterFromFile implements Consumer<Consumer<City>> {


    @Override
    public void accept(Consumer<City> update) throws Exception {
        try (JsonReader reader = jsonReader()) {
            Gson gson = new GsonBuilder().create();
            reader.beginArray();
            while (reader.hasNext()) update.accept(gson.fromJson(reader, City.class));
        }
    }

    private JsonReader jsonReader() {
        return App.getInstance()
                .map(ContextWrapper::getResources)
                .map(resources -> resources.openRawResource(R.raw.city_list))
                .map(stream -> new InputStreamReader(stream, "UTF-8"))
                .map(JsonReader::new)
                .blockingGet();

    }
}

