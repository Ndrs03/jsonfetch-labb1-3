package se.andreas;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JsonReader {
    private static final String apiUrl = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/17.29/lat/62.39/data.json";

    /**
    * Reads JSON data from member stringUrl and returns a JsonNode
    * @return a JsonNode containing the data from the URL
     */
    private static JsonNode readJsonFromApi() {
        // better exception handeling needed, maybe log file?
        try {
            URL url = new URL(apiUrl);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(url);
        }  catch (MalformedURLException e) {
            System.err.println("Malformed URL");
            return null;
        } catch (IOException e) {
            System.err.println("IOException");
            return null;
        }
    }

    /**
     * Returns a map with the data from the
     * @param key the key to search for
     *            t - temperature,
     *            spp - percent of precipitation in frozen form,
     *            pcat - category of precipitation,
     *            pmin - minimum precipitation intensity,
     *            pmean - mean precipitation intensity,
     *            pmax - maximum precipitation intensity,
     *            pmedian - median precipitation intensity,
     *            tcc_mean - mean value of total cloud cover,
     *            lcc_mean - mean value of low level cloud cover,
     *            mcc_mean - mean value of medium level cloud cover,
     *            hcc_mean - mean value of high level cloud cover,
     *            msl - air pressure,
     *            vis - horizontal visibility,
     *            wd - wind direction,
     *            ws - wind speed,
     *            r - relative humidity,
     *            tstm - thunder probability,
     *            gust - wind gust speed,
     *            Wsymb2 - weather symbol
     * @return a map with time and data from the JSON
     */
    public static Map<String, Double> getMapFromKey(String key) {
        JsonNode jsonData = readJsonFromApi();

        if (jsonData == null) {
            throw new RuntimeException("Failed to read JSON data");
        }
        JsonNode timeSeries = jsonData.get("timeSeries");
        if (timeSeries == null) {
            throw new RuntimeException("Failed to read timeSeries");
        }

        Map<String, Double> timeDataMap = new HashMap<>();
        // enter timeSeries
        for (JsonNode timePoint : timeSeries) {
            JsonNode validTime = timePoint.get("validTime");
            JsonNode parameters = timePoint.get("parameters");
            // enter parameters
            for (JsonNode parameter : parameters) {
                // find parameter that matches key
                if (key.equals(parameter.get("name").asText())) {
                    // get value for key, stored under values and 0: index
                    double data = parameter.get("values").get(0).asDouble();
                    timeDataMap.put(validTime.asText(), data);
                    break;
                }
            }
        }
        return timeDataMap;
    }

}