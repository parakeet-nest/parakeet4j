package org.parakeetnest.parakeet4j.history;

import org.parakeetnest.parakeet4j.llm.Message;
import org.parakeetnest.parakeet4j.llm.MessageRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
This is a WIP 🚧
 */
public class MemoryMessages {
    private Map<String, MessageRecord> messages;

    public MemoryMessages() {
        this.messages = new HashMap<>();
    }

    public MessageRecord get(String id) {
        return messages.get(id);
    }

    public Message getMessage(String id) {
        MessageRecord messageRecord = get(id);
        if (messageRecord == null) {
            return new Message();
        }
        return new Message(messageRecord.getRole(), messageRecord.getContent());
    }

    public List<MessageRecord> getAll() {
        return new ArrayList<>(messages.values());
    }

    public List<Message> getAllMessages() {
        List<Message> messageList = new ArrayList<>();
        for (MessageRecord messageRecord : messages.values()) {
            messageList.add(new Message(messageRecord.getRole(), messageRecord.getContent()));
        }
        return messageList;
    }

    public MessageRecord save(MessageRecord messageRecord) {
        messages.put(messageRecord.getId(), messageRecord);
        return messageRecord;
    }

    public MessageRecord saveMessage(String id, Message message) {
        MessageRecord messageRecord = new MessageRecord(id, message.getRole(), message.getContent());
        return save(messageRecord);
    }
}
