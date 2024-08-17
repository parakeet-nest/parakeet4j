package org.parakeetnest.parakeet4j.llm;

import java.util.List;

public class VectorRecord {
    private String id;
    private String prompt;
    private double[] embedding;
    private double cosineDistance;

    private String reference;
    private String metaData;
    private String text;

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

    public double[] getEmbedding() {
        return embedding;
    }

    public VectorRecord setEmbedding(double[] embedding) {
        this.embedding = embedding;
        return this;
    }

    public double getCosineDistance() {
        return cosineDistance;
    }

    public VectorRecord setCosineDistance(double cosineDistance) {
        this.cosineDistance = cosineDistance;
        return this;
    }

    public String getReference() {
        return reference;
    }

    public VectorRecord setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getMetaData() {
        return metaData;
    }

    public VectorRecord setMetaData(String metaData) {
        this.metaData = metaData;
        return this;

    }

    public String getText() {
        return text;
    }

    public VectorRecord setText(String text) {
        this.text = text;
        return this;
    }
}


