package org.parakeetnest.parakeet4j.llm;

import java.util.List;
import java.util.Map;

// For tools

public class Parameters {
    private String type;
    private Map<String, Property> properties;
    private List<String> required;

    public Parameters() {
    }

    public Parameters(String type, Map<String, Property> properties, List<String> required) {
        this.type = type;
        this.properties = properties;
        this.required = required;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Property> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Property> properties) {
        this.properties = properties;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }
}