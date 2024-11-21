
import java.util.Map;
import se.andreas.JsonReader;

public class MainActivity {
    public static void main(String[] args) {
        Map<String, Double> timeTemperatureMap = JsonReader.timeTemperatureMap;

        for (Map.Entry<String, Double> entry : timeTemperatureMap.entrySet()) {
            System.out.println("Time: " + entry.getKey() + ", Temperature: " + entry.getValue());
        }
    }
}
