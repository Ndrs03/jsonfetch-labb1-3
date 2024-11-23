package se.andreas.jsonStructure;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Parameter {
    @JsonProperty("name")
    private String name;

    @JsonProperty("levelType")
    private String levelType;

    @JsonProperty("level")
    private int level;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("values")
    private List<Double> values;

    // Getters

    public String getName() {
        return name;
    }

    public String getLevelType() {
        return levelType;
    }

    public int getLevel() {
        return level;
    }

    public String getUnit() {
        return unit;
    }

    public List<Double> getValues() {
        return values;
    }
}