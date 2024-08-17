package org.parakeetnest.parakeet4j.llm;

public class Query4Embedding {
    private String prompt;
    private String model;

    private String tokenHeaderName;
    private String tokenHeaderValue;

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

    public String getTokenHeaderName() {
        return tokenHeaderName;
    }

    public Query4Embedding setTokenHeaderName(String tokenHeaderName) {
        this.tokenHeaderName = tokenHeaderName;
        return this;
    }

    public String getTokenHeaderValue() {
        return tokenHeaderValue;
    }

    public Query4Embedding setTokenHeaderValue(String tokenHeaderValue) {
        this.tokenHeaderValue = tokenHeaderValue;
        return this;
    }


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

