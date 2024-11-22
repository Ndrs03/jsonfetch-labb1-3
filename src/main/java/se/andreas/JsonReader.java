package se.andreas;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JsonReader {
    private static String lon = "17.29";
    private static String lat = "62.39";
    private static final String apiUrl = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/"+lon+"/lat/"+lat+"/data.json";

    /**
     * Sets the longitude and latitude for the API
     * @param lon for position
     * @param lat for position
     */
    public static void setLonLat(String lon, String lat) {
        JsonReader.lon = lon;
        JsonReader.lat = lat;
    }

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
     * Returns a map with the data from the api
     * @param key the key to get data for, possible keys are:
     * <ul>
     *   <li>t - temperature</li>
     *   <li>spp - percent of precipitation in frozen form</li>
     *   <li>pcat - category of precipitation</li>
     *   <li>pmin - minimum precipitation intensity</li>
     *   <li>pmean - mean precipitation intensity</li>
     *   <li>pmax - maximum precipitation intensity</li>
     *   <li>pmedian - median precipitation intensity</li>
     *   <li>tcc_mean - mean value of total cloud cover</li>
     *   <li>lcc_mean - mean value of low level cloud cover</li>
     *   <li>mcc_mean - mean value of medium level cloud cover</li>
     *   <li>hcc_mean - mean value of high level cloud cover</li>
     *   <li>msl - air pressure</li>
     *   <li>vis - horizontal visibility</li>
     *   <li>wd - wind direction</li>
     *   <li>ws - wind speed</li>
     *   <li>r - relative humidity</li>
     *   <li>tstm - thunder probability</li>
     *   <li>gust - wind gust speed</li>
     *   <li>Wsymb2 - weather symbol</li>
     * </ul>
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