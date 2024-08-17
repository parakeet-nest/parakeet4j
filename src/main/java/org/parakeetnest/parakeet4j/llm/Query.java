package org.parakeetnest.parakeet4j.llm;

import io.vertx.core.json.JsonObject;

import java.util.List;

public class Query {

    public Query() {}

    public Query(String model) {
        this.model = model;
    }

    public Query(String model, Options options) {
        this.model = model;
        this.options = options;
    }

    public Query(String model, Options options, List<Message> messages) {
        this.model = model;
        this.messages = messages;
        this.options = options;
    }

    private String model;
    private List<Message> messages; // For Chat Completion
    private Options options;
    private boolean stream;
    private String prompt; // For "Simple" Completion
    private int[] context; // For "Simple" Completion

    private String format; // https://github.com/ollama/ollama/blob/main/docs/api.md#request-json-mode
    private boolean keepAlive;
    private boolean raw;
    private String system;
    private String template;

    private List<Tool> tools;

    private String tokenHeaderName;
    private String tokenHeaderValue;

    public String toJsonString() {
        var jsonObject = new JsonObject();
        jsonObject.put("model", model);
        jsonObject.put("messages", messages);
        jsonObject.put("options", options);
        jsonObject.put("stream", stream);
        jsonObject.put("prompt", prompt);
        jsonObject.put("context", context);
        jsonObject.put("format", format);
        jsonObject.put("keepAlive", keepAlive);
        jsonObject.put("raw", raw);
        jsonObject.put("system", system);
        jsonObject.put("template", template);
        jsonObject.put("tools", tools);
        return jsonObject.encodePrettily();
    }

    public String getTokenHeaderName() {
        return tokenHeaderName;
    }

    public Query setTokenHeaderName(String tokenHeaderName) {
        this.tokenHeaderName = tokenHeaderName;
        return this;
    }

    public String getTokenHeaderValue() {
        return tokenHeaderValue;
    }

    public Query setTokenHeaderValue(String tokenHeaderValue) {
        this.tokenHeaderValue = tokenHeaderValue;
        return this;
    }

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

    public int[] getContext() {
        return context;
    }

    public Query setContext(int[] context) {
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

    public List<Tool> getTools() {
        return tools;
    }

    public Query setTools(List<Tool> tools) {
        this.tools = tools;
        return this;
    }
}


