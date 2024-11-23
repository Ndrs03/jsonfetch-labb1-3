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

    /**
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
     * @return A map of time and data for the specific parameter
     */
    public Map<String, Double> getMapFromParameterName(String key) {
        Map<String, Double> timeDataMap = new HashMap<>();
        // enter the time series
        for (TimeSeries timeSeries : timeSeries) {
            Parameter parameter = timeSeries.getParameterByName(key);
            if (parameter == null) {
                throw new IllegalArgumentException("Key not found in time series");
            }
            // the weatherdata is in the first index of the values list
            double data = parameter.getValues().get(0);
            timeDataMap.put(timeSeries.getValidTime(), data);
        }
        return timeDataMap;
    }

}