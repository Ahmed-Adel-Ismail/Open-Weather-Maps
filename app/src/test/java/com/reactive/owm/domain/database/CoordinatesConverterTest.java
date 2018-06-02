package com.reactive.owm.domain.database;

import com.reactive.owm.entities.Coordinates;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CoordinatesConverterTest {

    @Test
    public void fromCoordinatesThenConvertToGson() {
        Coordinates coordinates = new Coordinates(1D, 1D);
        String result = new CoordinatesConverter().fromCoordinates(coordinates);
        assertEquals("{\"lat\":1.0,\"lon\":1.0}", result);
    }

    @Test
    public void fromStringThenConvertToCoordinates() {
        String value = "{\"lat\":1.0,\"lon\":1.0}";
        Coordinates coordinates = new CoordinatesConverter().fromString(value);
        assertTrue(coordinates.getLatitude().blockingGet() == 1
                && coordinates.getLongitude().blockingGet() == 1);
    }

}