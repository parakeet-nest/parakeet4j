package org.parakeetnest.parakeet4j.llm;

// For tools
public class Property {
    private String type;
    private String description;

    public Property() {
    }

    public Property(String type, String description) {
        this.type = type;
        this.description = description;
    }


    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}