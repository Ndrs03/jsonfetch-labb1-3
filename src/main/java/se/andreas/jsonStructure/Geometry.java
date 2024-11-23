package se.andreas.jsonStructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Geometry {
    @JsonProperty("type")
    private String type;

    @JsonProperty("coordinates")
    private List<List<Double>> coordinates;

    // Getters and setters
}
