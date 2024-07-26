package org.parakeetnest.parakeet4j.llm;

// For tools

public class Tool {
    private String type;
    private Function function;

    public Tool() {
    }

    public Tool(String type, Function function) {
        this.type = type;
        this.function = function;
    }



    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
