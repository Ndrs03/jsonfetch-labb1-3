package se.andreas.jsonStructure;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Class for the parameter from SMHI API
 */
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

    /**
     * @return The name of the parameter that is measured
     */
    public String getName() {
        return name;
    }

    /**
     * @return The values of the parameter
     */
    public List<Double> getValues() {
        return values;
    }
}