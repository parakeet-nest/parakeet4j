package org.parakeetnest.parakeet4j.llm;

public class MessageRecord {
    private String id;
    private String role;
    private String content;

    public String getId() {
        return id;
    }

    public MessageRecord setId(String id) {
        this.id = id;
        return this;
    }

    public String getRole() {
        return role;
    }

    public MessageRecord setRole(String role) {
        this.role = role;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageRecord setContent(String content) {
        this.content = content;
        return this;
    }
}


