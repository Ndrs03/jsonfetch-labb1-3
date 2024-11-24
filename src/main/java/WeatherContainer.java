import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.andreas.APIfetcher;
import se.andreas.WeatherData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Container class that stores a specific object {@code WeatherObject} that includes multiple {@code Map} objects with the time as keys and the values as temperature, wind speed, cloud cover and precipitation.
 * Uses the {@code APIfetcher} to fetch the data from the SMHI API.
 * This uses retrofit to fetch the data asynchronously.
 * This class is used as a handler for the {@code WeatherObject} class and the {@code APIfetcher} class.
 * @see WeatherObject
 * @see APIfetcher
 */
public class WeatherContainer {
    /** Map with city name as keys and the {@code WeatherObject} as values */
    Map<String, WeatherObject> container;

    /**
     * Constructor to initialize the {@code WeatherContainer} with an empty map.
     */
    public WeatherContainer() {
        container = new HashMap<>();
    }

    /**
     * Insert a new {@code WeatherObject} into the container with the provided city name.
     * This fetches the data from the SMHI API and stores it in the {@code WeatherObject}.
     * This is done asynchronously by using multiple threads.
     * The function is aslo used as the handler for the {@code APIfetcher} class.
     * @param cityName The name of the city to fetch the weather data for
     */
    public void insertObject(String cityName) {
        String[] keys = new String[]{"t", "ws", "tcc_mean", "pmean", "Wsymb2"}; // Parameters to fetch
        Map<String, Double>[] maps = new Map[keys.length];  // Maps to store the data
        CountDownLatch latch = new CountDownLatch(keys.length); // Latch to wait for all threads to finish
        boolean[] errors = new boolean[keys.length];    // Array to store if any of the threads failed

        for (int i = 0; i < keys.length; i++) { // Create a new map for each parameter
            maps[i] = new HashMap<>();
            new MapMiltiRunner(keys[i], maps[i], latch, errors, i).run();
        }

        try {   // Wait for all threads to finish
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (boolean error : errors) {  // Check if any of the threads failed
            if (error) {
                System.err.println("Failed to fetch weather data for " + cityName);
                return;
            }
        }

        container.put(cityName, new WeatherObject(  // Insert the new WeatherObject into the container
                maps[0],
                maps[1],
                maps[2],
                maps[3],
                maps[4]
        ));
    }

    /**
     * Get the {@code WeatherObject} from the container with the provided city name.
     * @param cityName The name of the city to get the weather data for.
     * @return The {@code WeatherObject} for the provided city name.
     */
    public WeatherObject getObject(String cityName) {
        return container.get(cityName);
    }

/**
 * Runnable class to fetch the weather data for a specific parameter.
 * This is used to fetch the data asynchronously and therefore uses multiple threads.
 * The class itself is using override to implement a costumized retrofit callback.
 *
 * @see retrofit2.Retrofit
 * @see Callback
 * @see Response
 */
    private record MapMiltiRunner(String key, Map<String, Double> map, CountDownLatch latch, boolean[] errors,
                                  int index) implements Runnable {

        /**
         * Fetch the weather data for a specific parameter.
         * This is done asynchronously by using multiple threads.
         * The function calls {@code APIfetcher} class and uses the retrofit to fetch the data.
         * The function also checks if the data was fetched successfully and stores the data in the map.
         * This is done via api call to the retrofit client.
         */
        @Override
        public void run() {
            APIfetcher.getWeatherData(new Callback<>() {
                @Override
                public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                    if (!response.isSuccessful() && response.body() == null) {
                        errors[index] = true;
                        latch.countDown();
                        return;
                    }
                    WeatherData weatherData = response.body();
                    map.putAll(weatherData.getMapFromParameterName(key));
                    latch.countDown();
                }
                @Override
                public void onFailure(Call<WeatherData> call, Throwable t) {
                    System.err.println("Failed to fetch weather data: " + t.getMessage());
                    errors[index] = true;
                    latch.countDown();
                }
            });
        }
    }
}