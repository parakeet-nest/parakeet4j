package org.parakeetnest.parakeet4j.llm;

import java.util.List;

public class VectorRecord {
    private String id;
    private String prompt;
    private List<Double> embedding;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public VectorRecord setId(String id) {
        this.id = id;
        return this;
    }

    public String getPrompt() {
        return prompt;
    }

    public VectorRecord setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }

    public VectorRecord setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
        return this;
    }
}


