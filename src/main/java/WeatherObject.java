import java.util.Map;

/**
 * Object class that stores a specific weather object that includes maps with time and as values and temperature, wind speed, cloud cover and percipitation as keys.
 *
 */
public class WeatherObject {
    /** Map with time as keys and temperature as values */
    Map<String, Double> temperature;

    /** Map with time as keys and wind speed as values */
    Map<String, Double> windSpeed;

    /** Map with time as keys and cloud cover as values */
    Map<String, Double> cloudCover;

    /** Map with time as keys and percipitation as values */
    Map<String, Double> percipitation;

    /** Map with time as keys and weatherSymbol as values */
    Map<String, Double> weatherSymbol;

    /**
     * Constructor to initialize the WeatherObject with the provided data maps.
     *
     * @param temperature  Map with time as keys and temperature as values
     * @param windSpeed    Map of time as keys and wind speed as values
     * @param cloudCover   Map of time as keys and cloud cover as values
     * @param percipitation Map of time as keys and percipitation as values
     * @param weatherSymbol Map of time as keys and weatherSymbol as values
     */
    public WeatherObject(Map<String, Double> temperature, Map<String, Double> windSpeed,
                         Map<String, Double> cloudCover, Map<String, Double> percipitation,
                         Map<String,Double> weatherSymbol) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.cloudCover = cloudCover;
        this.percipitation = percipitation;
        this.weatherSymbol = weatherSymbol;
    }

    /**
     * Gets the temperature map.
     *
     * @return Map with time as keys and temperature as values
     */
    public Map<String, Double> getTemperature() {
        return temperature;
    }

    /**
     * Gets the wind speed map.
     *
     * @return Map with time as keys and wind speed as values
     */
    public Map<String, Double> getWindSpeed() {
        return windSpeed;
    }

    /**
     * Gets the cloud cover map.
     *
     * @return Map with time as keys and cloud cover as values in form of precentage
     */
    public Map<String, Double> getCloudCover() {
        return cloudCover;
    }

    /**
     * Gets the percipitation map.
     *
     * @return Map with time as keys and percipitation as values in form of precentage
     */
    public Map<String, Double> getPercipitation() {
        return percipitation;
    }
}
