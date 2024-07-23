package org.parakeetnest.parakeet4j.llm;

public class Query4Embedding {
    private String prompt;
    private String model;

    public Query4Embedding() {}

    public Query4Embedding(String model) {
        this.model = model;
    }

    public Query4Embedding(String model, String prompt) {
        this.prompt = prompt;
        this.model = model;
    }

    /*
    public Query4Embedding(String prompt, String model) {
        this.prompt = prompt;
        this.model = model;
    }
    */

    public String getPrompt() {
        return prompt;
    }

    public Query4Embedding setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public String getModel() {
        return model;
    }

    public Query4Embedding setModel(String model) {
        this.model = model;
        return this;
    }
}

