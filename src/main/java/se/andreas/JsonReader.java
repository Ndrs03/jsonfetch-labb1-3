package se.andreas;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JsonReader {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static final Map<String, Double> timeTemperatureMap = getTemperatureMap();





    private static JsonNode readJsonFromUrl(String stringUrl) {
        // better exception handeling needed
        try {
            URL url = new URL(stringUrl);
            return mapper.readTree(url);
        }  catch (MalformedURLException e) {
            System.err.println("Malformed URL");
            return null;
        } catch (IOException e) {
            System.err.println("IOException");
            return null;
        }
    }

    private static Map<String, Double> getTemperatureMap() {
        JsonNode jsonData = readJsonFromUrl("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/17.29/lat/62.39/data.json");
        if (jsonData == null) {
            throw new RuntimeException("Failed to read JSON data");
        }
        JsonNode timeSeries = jsonData.get("timeSeries");
        if (timeSeries == null) {
            throw new RuntimeException("Failed to read timeSeries");
        }
        Map<String, Double> timeTemperatureMap = new HashMap<>();
        for (JsonNode timePoint : timeSeries) {
            JsonNode validTime = timePoint.get("validTime");
            JsonNode parameters = timePoint.get("parameters");
            for (JsonNode parameter : parameters) {
                if ("t".equals(parameter.get("name").asText())) {
                    double temperature = parameter.get("values").get(0).asDouble();
                    timeTemperatureMap.put(validTime.asText(), temperature);
                    break;
                }
            }
        }
        return timeTemperatureMap;
    }
}