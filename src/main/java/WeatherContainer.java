import java.util.HashMap;
import java.util.Map;

import se.andreas.JsonReader;

/**
 * Object class that stores weather objects inside a container. The container is a map with city names as keys and WeatherObjects as values.
 *
 * @see WeatherObject
 */
public class WeatherContainer {
    /** Map with city names as keys and WeatherObjects as values. */
    Map<String, WeatherObject> container;

    /**
     * Default constructor.
     *
     */
    public WeatherContainer() {
        container = new HashMap<>();
    }

    /**
     * Insert object into container.
     *
     * @param cityName Name of the city to insert the object for.
     * @param object WeatherObject to insert.
     */
    public void insertObject(String cityName) { // Insert object into container
        container.put(cityName, new WeatherObject(JsonReader.getMapFromKey("t"), JsonReader.getMapFromKey("ws"),
                JsonReader.getMapFromKey("tcc_mean"), JsonReader.getMapFromKey("pmean"),
                JsonReader.getMapFromKey("Wsymb2")));
    }

    /**
     * Get object from container.
     *
     * @param cityName Name of the city to get the object for.
     * @return WeatherObject for the city.
     */
    public WeatherObject getObject(String cityName) { // Get object from container
        return container.get(cityName);
    }
}