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


    public static final Map<String, Double> timeTemperatureMap = getMapFromKey("Wsymb2");

    /**
    * Reads JSON data from a URL and returns a JsonNode
    * @param stringUrl the URL to read from
    * @return a JsonNode containing the data from the URL
     */
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

    /**
     * Returns a map with the data from the
     * @param key the key to search for
     *            t - temperature,
     *            spp - precipitation in frozen form,
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
    private static Map<String, Double> getMapFromKey(String key) {
        JsonNode jsonData = readJsonFromUrl("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/17.29/lat/62.39/data.json");
        if (jsonData == null) {
            throw new RuntimeException("Failed to read JSON data");
        }
        JsonNode timeSeries = jsonData.get("timeSeries");
        if (timeSeries == null) {
            throw new RuntimeException("Failed to read timeSeries");
        }
        Map<String, Double> timeDataMap = new HashMap<>();
        // do i really need this loop?
        for (JsonNode timePoint : timeSeries) {
            JsonNode validTime = timePoint.get("validTime");
            JsonNode parameters = timePoint.get("parameters");
            for (JsonNode parameter : parameters) {
                if (key.equals(parameter.get("name").asText())) {
                    double data = parameter.get("values").get(0).asDouble();
                    timeDataMap.put(validTime.asText(), data);
                    break;
                }
            }
        }
        return timeDataMap;
    }




}