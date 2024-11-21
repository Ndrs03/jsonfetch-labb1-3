import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import se.andreas.JsonReader;

public class MainActivity {
    public static void main(String[] args) {
        Map<String, Double> timeTemperatureMap = JsonReader.getMapFromKey("t");

        // sorted print
        SortedSet<String> keys = new TreeSet<>(timeTemperatureMap.keySet());
        for (String key : keys) {
            Double value = timeTemperatureMap.get(key);
            System.out.println("Time: " + key + ", Temperature: " + value);
        }

    }
}
