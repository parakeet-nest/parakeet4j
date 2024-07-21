package org.parakeetnest.parakeet4j;

import org.parakeetnest.parakeet4j.llm.Message;
import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import java.util.List;

import static org.parakeetnest.parakeet4j.completion.Completion.Chat;

public class DemoChat
{
    public static void main( String[] args )
    {

        Options options = new Options();
        options.setTemperature(0.0)
                .setRepeatLastN(2);

        var systemContent = "You are a useful AI agent, expert with the Star Trek franchise.";
        var userContent = "Who is Jean-Luc Picard";

        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query();
        queryChat.setModel("tinyllama")
                .setMessages(messages)
                .setOptions(options);


        Chat("http://0.0.0.0:11434", queryChat,
                answer -> {
                    System.out.println("😛: " + answer.getMessage().getContent());
                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                });

    }
}
