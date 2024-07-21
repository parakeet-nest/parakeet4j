package org.parakeetnest.parakeet4j.llm;

import java.time.LocalDateTime;

public class Answer {
    private String model;
    private Message message; // For Chat Completion
    private boolean done;
    private String response; // For "Simple" Completion
    private int[] context; // For "Simple" Completion

    private LocalDateTime createdAt;
    private long totalDuration;
    private int loadDuration;
    private int promptEvalCount;
    private int promptEvalDuration;
    private int evalCount;
    private long evalDuration;

    public String getModel() {
        return model;
    }

    public Answer setModel(String model) {
        this.model = model;
        return this;
    }

    public Message getMessage() {
        return message;
    }

    public Answer setMessage(Message message) {
        this.message = message;
        return this;
    }

    public boolean isDone() {
        return done;
    }

    public Answer setDone(boolean done) {
        this.done = done;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public Answer setResponse(String response) {
        this.response = response;
        return this;
    }

    public int[] getContext() {
        return context;
    }

    public Answer setContext(int[] context) {
        this.context = context;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Answer setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public Answer setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
        return this;
    }

    public int getLoadDuration() {
        return loadDuration;
    }

    public Answer setLoadDuration(int loadDuration) {
        this.loadDuration = loadDuration;
        return this;
    }

    public int getPromptEvalCount() {
        return promptEvalCount;
    }

    public Answer setPromptEvalCount(int promptEvalCount) {
        this.promptEvalCount = promptEvalCount;
        return this;
    }

    public int getPromptEvalDuration() {
        return promptEvalDuration;
    }

    public Answer setPromptEvalDuration(int promptEvalDuration) {
        this.promptEvalDuration = promptEvalDuration;
        return this;
    }

    public int getEvalCount() {
        return evalCount;
    }

    public Answer setEvalCount(int evalCount) {
        this.evalCount = evalCount;
        return this;
    }

    public long getEvalDuration() {
        return evalDuration;
    }

    public Answer setEvalDuration(long evalDuration) {
        this.evalDuration = evalDuration;
        return this;
    }
}

