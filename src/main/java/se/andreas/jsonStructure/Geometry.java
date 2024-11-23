package se.andreas.jsonStructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Class for the geometry from SMHI API
 * Unused for now but jackson becomes angry if it is not here
 */
public class Geometry {
    @JsonProperty("type")
    private String type;

    @JsonProperty("coordinates")
    private List<List<Double>> coordinates;

}
