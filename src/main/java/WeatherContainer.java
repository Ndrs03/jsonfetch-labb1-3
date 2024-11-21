import java.util.Map;

/**
 * Object class that stores weather objects inside a container. The container is a map with city names as keys and WeatherObjects as values.
 *
 * @see WeatherObject
 */
public class WeatherContainer {
    Map<String, WeatherObject> container; /** Map with city names as keys and WeatherObjects as values. */

    /**
     * Default constructor.
     *
     */
    public WeatherContainer() {
        // Default constructor
    }

    /**
     * Insert object into container.
     *
     * @param cityName Name of the city to insert the object for.
     * @param object WeatherObject to insert.
     */
    private void insertObject(String cityName, WeatherObject object) { // Insert object into container
        container.put(cityName, object);
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