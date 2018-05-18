package com.reactive.owm.entities;


import io.reactivex.Maybe;

public class ForecastSummery {

    private final String dateText;
    private final String cloudiness;
    private final String humidity;
    private final String temperature;
    private final String weather;
    private final String windSpeed;

    private ForecastSummery(Builder builder) {
        dateText = builder.dateText;
        cloudiness = builder.cloudiness;
        humidity = builder.humidity;
        temperature = builder.temperature;
        weather = builder.weather;
        windSpeed = builder.windSpeed;
    }

    public Maybe<String> getDateText() {
        return dateText != null ? Maybe.just(dateText) : Maybe.empty();
    }

    public Maybe<String> getCloudiness() {
        return cloudiness != null ? Maybe.just(cloudiness) : Maybe.empty();
    }

    public Maybe<String> getHumidity() {
        return humidity != null ? Maybe.just(humidity) : Maybe.empty();
    }

    public Maybe<String> getTemperature() {
        return temperature != null ? Maybe.just(temperature) : Maybe.empty();
    }

    public Maybe<String> getWeather() {
        return weather != null ? Maybe.just(weather) : Maybe.empty();
    }

    public Maybe<String> getWindSpeed() {
        return windSpeed != null ? Maybe.just(windSpeed) : Maybe.empty();
    }

    @Override
    public String toString() {
        return "ForecastSummery{" +
                "dateText='" + dateText + '\'' +
                ", cloudiness='" + cloudiness + '\'' +
                ", humidity='" + humidity + '\'' +
                ", temperature='" + temperature + '\'' +
                ", weather='" + weather + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                '}';
    }

    public static final class Builder {
        private String dateText;
        private String cloudiness;
        private String humidity;
        private String temperature;
        private String weather;
        private String windSpeed;

        public Builder() {
        }

        public Builder dateText(String dateText) {
            this.dateText = dateText;
            return this;
        }

        public Builder cloudiness(String cloudiness) {
            this.cloudiness = cloudiness;
            return this;
        }

        public Builder humidity(String humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder temperature(String temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder weather(String weather) {
            this.weather = weather;
            return this;
        }

        public Builder windSpeed(String windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        public ForecastSummery build() {
            return new ForecastSummery(this);
        }
    }
}
