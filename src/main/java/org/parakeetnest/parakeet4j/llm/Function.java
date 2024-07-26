package org.parakeetnest.parakeet4j.llm;

// For tools

public class Function {
    private String name;
    private String description;
    private Parameters parameters;

    public Function() {
    }

    public Function(String name, Parameters parameters, String description) {
        this.name = name;
        this.description = description;
        this.parameters = parameters;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }
}