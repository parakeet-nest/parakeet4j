package examples.prompts_samples;

import org.parakeetnest.parakeet4j.llm.Message;
import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import java.util.List;

import static org.parakeetnest.parakeet4j.completion.Completion.ChatStream;

public class PineapplePizza
{
    public static void main( String[] args )
    {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        var systemContent = """
        You are a pizza expert, use the given context to answer.
        """;

        var contextContent = """
        Context:
        A group of renowned chefs from around the world gathered to discuss the most innovative and delicious pizzas. 
        Many expressed their love for unique and bold flavors, especially those that combine sweet and savory elements. 
        Among the most talked-about was the pineapple pizza, praised for its refreshing taste and unique blend of ingredients.
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
