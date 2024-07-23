package examples.prompts_samples;

import org.parakeetnest.parakeet4j.llm.Message;
import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import java.util.List;

import static org.parakeetnest.parakeet4j.completion.Completion.ChatStream;

public class PineapplePizza2
{
    public static void main( String[] args )
    {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        var systemContent = """
        You are a world-renowned pizza expert who has traveled the globe tasting every conceivable type of pizza. 
        Your unconventional opinions often surprise people, but your expertise is undeniable. 
        You've written several best-selling books on pizza and your pizza reviews can make or break a pizzeria's reputation.
        
        Please start your explanation with "After tasting thousands of pizzas across the world, I can confidently say that the best pizza in the world is...
        """;

        var contextContent = """
        Context:
        Based on your extensive experience and expertise, explain why you believe the pinaapple pizza is the absolute best in the world. 
        Provide at least three compelling reasons for your choice, considering factors such as flavor combination, texture, and culinary innovation.
        """;

        var userContent = """
        Which pizza would you say is the best in the world, and why?               
        """;

        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("system", contextContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query("qwen2:0.5b", options).setMessages(messages);

        var resultAnswer = ChatStream("http://0.0.0.0:11434", queryChat,
                chunk -> {
                    System.out.print(chunk.getMessage().getContent());
                    return null;
                });

        if(resultAnswer.exception().isPresent()) {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }

    }
}
