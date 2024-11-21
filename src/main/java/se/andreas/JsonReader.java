package se.andreas;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JsonReader {
    private static final ObjectMapper mapper = new ObjectMapper();
    static int apiParameter = 16;
    // id 127240 är stationen norr om granlo
    private static String baseUrl = "https://opendata-download-metobs.smhi.se/api/version/latest/parameter/"+ apiParameter +"/station/127240.json";

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


    private String getPeriodNames(String parameterKey, String stationKey) throws IOException, JSONException {

        JsonNode jsonData = readJsonFromUrl("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/17.29/lat/62.39/data.json");


        String periodName = null;
        for (int i = 0; i < periodsArray.length(); i++) {
            periodName = periodsArray.getJSONObject(i).getString("key");
            System.out.println(periodName);
        }

        return periodName;
    }


    /**
     * Get the data for the given parameter, station and period.
     *
     * @param parameterKey The key for the wanted parameter
     * @param stationKey The key for the wanted station
     * @param periodName The name for the wanted period
     * @return The data
     * @throws IOException
     * @throws JSONException
     */
    private String getData(String parameterKey, String stationKey, String periodName) throws IOException {
        return readStringFromUrl(+ "/period/" + periodName + "/data.csv");
    }
}