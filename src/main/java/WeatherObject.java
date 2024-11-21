public class WeatherObject {
    double temperature;
    double windSpeed;
    double cloudCover;
    double percipitation;


    public WeatherObject() {}

    private void loadWeatherData() {
        // ADD THE JSON GETTERS
    }

    // GETTERS BELLOW
    public double getTemperature() {
        return temperature;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public double getCloudCover() {
        return cloudCover;
    }
    public double getPercipitation() {
        return percipitation;
    }
}
