package org.parakeetnest.parakeet4j.llm;

import java.util.List;

public class Query {
    private String model;
    private List<Message> messages; // For Chat Completion
    private Options options;
    private boolean stream;
    private String prompt; // For "Simple" Completion
    private List<Integer> context; // For "Simple" Completion

    private String format; // https://github.com/ollama/ollama/blob/main/docs/api.md#request-json-mode
    private boolean keepAlive;
    private boolean raw;
    private String system;
    private String template;

    // Getters and Setters
    public String getModel() {
        return model;
    }

    public Query setModel(String model) {
        this.model = model;
        return this;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Query setMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Options getOptions() {
        return options;
    }

    public Query setOptions(Options options) {
        this.options = options;
        return this;
    }

    public boolean isStream() {
        return stream;
    }

    public Query setStream(boolean stream) {
        this.stream = stream;
        return this;
    }

    public String getPrompt() {
        return prompt;
    }

    public Query setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public List<Integer> getContext() {
        return context;
    }

    public Query setContext(List<Integer> context) {
        this.context = context;
        return this;
    }

    public String getFormat() {
        return format;
    }

    public Query setFormat(String format) {
        this.format = format;
        return this;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public Query setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
        return this;
    }

    public boolean isRaw() {
        return raw;
    }

    public Query setRaw(boolean raw) {
        this.raw = raw;
        return this;
    }

    public String getSystem() {
        return system;
    }

    public Query setSystem(String system) {
        this.system = system;
        return this;
    }

    public String getTemplate() {
        return template;
    }

    public Query setTemplate(String template) {
        this.template = template;
        return this;
    }
}


