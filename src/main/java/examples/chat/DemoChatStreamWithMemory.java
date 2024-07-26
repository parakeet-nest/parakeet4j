package examples.chat;

import org.parakeetnest.parakeet4j.history.MemoryMessages;
import org.parakeetnest.parakeet4j.llm.Message;
import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import java.util.List;

import static org.parakeetnest.parakeet4j.completion.Completion.ChatStream;

public class DemoChatStreamWithMemory
{
    public static void main( String[] args )
    {
        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        //var conversation = new MemoryMessages();

        var systemContent = "You are a useful AI agent, expert with the Star Trek franchise.";
        var userContent = "Who is James T Kirk?";

        List<Message> messages = new java.util.ArrayList<>(List.of(
                new Message("system", systemContent),
                new Message("user", userContent)
        ));

        Query queryChat = new Query("tinyllama", options, messages);

        var resultAnswer = ChatStream("http://0.0.0.0:11434", queryChat,
                chunk -> {
                    System.out.print(chunk.getMessage().getContent());
                    return null;
                });

        if(resultAnswer.exception().isPresent()) {
            System.out.println("😡: " + resultAnswer.exception().toString());
            System.exit(1);
        }

        // Add the answer to the list of the messages
        messages.add(new Message("ai", resultAnswer.getAnswer().getMessage().getContent()));

        var nextUserContent = "Who is his best friend?";

        // Add the new question to the list of the messages
        messages.add(new Message("user", nextUserContent));

        Query nextQueryChat = new Query("tinyllama", options, messages);

        System.out.println();
        System.out.println("--------------------------------------");

        var nextResultAnswer = ChatStream("http://0.0.0.0:11434", nextQueryChat,
                chunk -> {
                    System.out.print(chunk.getMessage().getContent());
                    return null;
                });

        if(nextResultAnswer.exception().isPresent()) {
            System.out.println("😡: " + nextResultAnswer.exception().toString());
            System.exit(1);
        }
    }
}
