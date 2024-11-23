package se.andreas.jsonStructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Class for the time series from SMHI API
 */
public class TimeSeries {
    @JsonProperty("validTime")
    private String validTime;

    @JsonProperty("parameters")
    private List<Parameter> parameters;

    /**
     * @return The time the specific one hour forecast is for
     */
    public String getValidTime() {
        return validTime;
    }

    /**
     * @param name the key to get data for, possible keys are:
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
     * @return Parameter that corresponds to the name
     */
    public Parameter getParameterByName(String name) {
        for (Parameter parameter : parameters) {
            if (parameter.getName().equals(name)) {
                return parameter;
            }
        }
        return null;
    }

}
