package se.andreas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.andreas.jsonStructure.Geometry;
import se.andreas.jsonStructure.TimeSeries;

import java.lang.reflect.GenericArrayType;
import java.util.List;

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


    public String getReferenceTime() {
        return referenceTime;
    }


    /**
     * Get the data for all hours
     * @return
     */
    public List<TimeSeries> getTimeSeries() {
        return timeSeries;
    }

}