package com.example.whereto.models;

import com.google.gson.annotations.SerializedName;

public class WeatherService {
    @SerializedName("current")
    private CurrentWeather currentWeather;

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public double getTemp() {
        return currentWeather.getTemp();
    }

    public class CurrentWeather {
        @SerializedName("temp_f")
        private double temp;
        @SerializedName("feelslike_f")
        private double feelsLike;
        @SerializedName("condition")
        private Condition condition;
        @SerializedName("wind_mph")
        private double wind;
        @SerializedName("wind_dir")
        private String direction;
        @SerializedName("precip_in")
        private double precipitation;
        @SerializedName("humidity")
        private double humidity;
        @SerializedName("uv")
        private double uv;

        public double getTemp() {
            return temp;
        }

        public double getFeelsLike() {
            return feelsLike;
        }

        public double getWind() {
            return wind;
        }

        public String getDirection() {
            return direction;
        }

        public double getPrecipitation() {
            return precipitation;
        }

        public double getHumidity() {
            return humidity;
        }

        public double getUv() {
            return uv;
        }

        public String getWeatherCondition() {
            return condition.getCondition();
        }

        public String getWeatherIcon() {
            return condition.getWeatherIcon();
        }


        public class Condition {
            @SerializedName("text")
            private String weatherCondition;
            @SerializedName("icon")
            private String weatherIcon;

            public String getCondition() {
                return weatherCondition;
            }

            public String getWeatherIcon() {
                return weatherIcon;
            }
        }
    }
}
