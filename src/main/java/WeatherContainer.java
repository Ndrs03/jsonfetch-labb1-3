import java.util.Map;




public class WeatherContainer {
    Map<String, WeatherObject> container; // Container for weather data objects

    public WeatherContainer() { // Constructor

    }

    public WeatherObject getObject(String cityName) { // Get object from container
        return container.get(cityName);
    }



}
