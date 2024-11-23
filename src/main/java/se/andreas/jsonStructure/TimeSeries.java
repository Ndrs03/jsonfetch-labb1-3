package se.andreas.jsonStructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TimeSeries {
    @JsonProperty("validTime")
    private String validTime;

    @JsonProperty("parameters")
    private List<Parameter> parameters;

    public String getValidTime() {
        return validTime;
    }

    public String getParameterName() {
        return parameters.get(0).getName();
    }

    public String getParameterValue() {
        return parameters.get(0).getValues().get(0).toString();
    }

    public String getParameterUnit() {
        return parameters.get(0).getUnit();
    }

    public String getParameterLevelType() {
        return parameters.get(0).getLevelType();
    }

    public int getParameterLevel() {
        return parameters.get(0).getLevel();
    }


}
