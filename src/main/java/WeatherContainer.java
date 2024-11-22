import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.andreas.JsonReader;

/**
 * Object class that stores weather objects {@code WeatherObject} inside a container {@code WeatherContainer}. The container is a map with city names as keys and WeatherObjects as values.
 *
 * @see WeatherObject
 * @see MapMiltiRunner
 * @see JsonReader
 */
public class WeatherContainer {
    /** {@code Map} with city names as keys and WeatherObjects as values. */
    Map<String, WeatherObject> container;

    /**
     * Constructor which implements the container as a {@code HashMap}.
     *
     */
    public WeatherContainer() {
        container = new HashMap<>();
    }

    /**
     * Inserts object {@code WeatherObject} into container.
     * This is done by creating runners and threads for each key in the object.
     * The runners are then started and the threads are joined.
     * The object is then inserted into the container after calling the constructor for {@code WeatherObject} with
     * the getters of the runners as parameters.
     * This is alter pushed into the container with the city name as key.
     *
     * @param cityName Name of the city to insert the object for.
     *
     * @see MapMiltiRunner
     * @see WeatherObject
     * @see JsonReader
     */
    public void insertObject(String cityName) {
        String[] keys = new String[]{"t", "ws", "tcc_mean", "pmean", "Wsymb2"};
        List<MapMiltiRunner> runners = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (String key : keys) {   // Create runners and threads.
            MapMiltiRunner runner = new MapMiltiRunner(key);
            runners.add(runner);
            threads.add(new Thread(runner));
        }

        for (Thread thread : threads) { // Start threads.
            thread.start();
        }

        for (Thread thread : threads) { // Wait for threads to finish.
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        container.put(cityName, new WeatherObject(  // Insert object into container by calling object constructor.
                runners.get(0).getResult(),
                runners.get(1).getResult(),
                runners.get(2).getResult(),
                runners.get(3).getResult(),
                runners.get(4).getResult()
        ));
    }

    /**
     * Get object from container.
     *
     * @param cityName Name of the city to get the object for.
     * @return {@code WeatherObject} for the city.
     */
    public WeatherObject getObject(String cityName) {return container.get(cityName);}

    /**
     * Inner class that implements {@code Runnable} to get a {@code Map} from a key.
     * The {@code Map} is stored as a field and can be retrieved with {@code getResult()}.
     */
    private class MapMiltiRunner implements Runnable {
        /** Key which we get data for. */
        private final String key;
        /** Map with time as keys and data as values. */
        private Map<String, Double> map;

        /**
         * Constructor to initialize the runner with a key.
         *
         * @param para string key to use for json reader call.
         */
        public MapMiltiRunner(String para) {
            this.key = para;
        }

        /**
         * Run method which gets the map from the key.
         * This is done by calling {@code JsonReader} with the key as parameter.
         * @see JsonReader
         */
        @Override
        public void run() {
            map = JsonReader.getMapFromKey(key);
        }

        /**
         * Getter for the requested map.
         *
         * @return {@code Map} with time as keys and data as values.
         */
        public Map<String, Double> getResult() {
            return map;
        }
    }
}