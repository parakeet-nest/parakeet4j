package org.parakeetnest.parakeet4j.llm;

public class Message {
    private String role;
    private String content;
    private String toolCalls;

    public Message() {}
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public Message(String role, String content, String toolCalls) {
        this.role = role;
        this.content = content;
        this.toolCalls = toolCalls;
    }

    public String getRole() {
        return role;
    }

    public Message setRole(String role) {
        this.role = role;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public String getToolCalls() {
        return toolCalls;
    }

    public Message setToolCalls(String toolCalls) {
        this.toolCalls = toolCalls;
        return this;
    }
}

