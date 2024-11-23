package se.andreas;

import com.fasterxml.jackson.annotation.JsonProperty;
import se.andreas.jsonStructure.Geometry;
import se.andreas.jsonStructure.Parameter;
import se.andreas.jsonStructure.TimeSeries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for the weather data from SMHI API
 */
public class WeatherData {

    @JsonProperty("approvedTime")
    private String approvedTime;

    @JsonProperty("referenceTime")
    private String referenceTime;

    @JsonProperty("geometry")
    private Geometry geometry;

    @JsonProperty("timeSeries")
    private List<TimeSeries> timeSeries;

    /**
     * @return The time the forecast was updated
     */
    public String getApprovedTime() {
        return approvedTime;
    }

    /**
     * @return The time the forecast is for
     */
    public String getReferenceTime() {
        return referenceTime;
    }


    /**
     * @return List of all time series .get(int) to get a specific time. 0 should be 1h from reference time then, +1h for each index. Dont rely on this use .get(int).getValidTime() instead
     */
    public List<TimeSeries> getTimeSeries() {
        return timeSeries;
    }

    public Map<String, Double> getMapFromParameterName(String key) {
        Map<String, Double> timeDataMap = new HashMap<>();
        for (TimeSeries timeSeries : timeSeries) {
            Parameter parameter = timeSeries.getParameterByName(key);
            if (parameter != null) {
                double data = parameter.getValues().get(0);
                timeDataMap.put(timeSeries.getValidTime(), data);
            }
        }
        return timeDataMap;
    }

}