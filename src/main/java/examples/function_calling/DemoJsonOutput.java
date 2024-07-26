package examples.function_calling;

import org.parakeetnest.parakeet4j.llm.Message;
import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import java.util.List;

import static org.parakeetnest.parakeet4j.completion.Completion.Chat;
import static org.parakeetnest.parakeet4j.completion.Completion.Generate;

public class DemoJsonOutput
{
    public static void main( String[] args )
    {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2)
                .setRepeatPenalty(2.0);

        var systemContent = """
        You are a helpful AI assistant. The user will enter the name of an animal.
        The assistant will then return the following information about the animal:
        - the scientific name of the animal (the name of json field is: scientific_name)
        - the main species of the animal  (the name of json field is: main_species)
        - the decimal average length of the animal (the name of json field is: average_length)
        - the decimal average weight of the animal (the name of json field is: average_weight)
        - the decimal average lifespan of the animal (the name of json field is: average_lifespan)
        - the countries where the animal lives into json array of strings (the name of json field is: countries)
        Output the results in JSON format and trim the spaces of the sentence.
        Use the provided context to give the data
        """;
        var userContent = "chicken";

        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("user", userContent)
        );

        //Query queryChat = new Query("phi3:mini", options)
        Query queryChat = new Query("qwen2:0.5b", options)
                .setMessages(messages)
                .setRaw(true)
                .setFormat("json");

        var resultAnswer = Chat("http://0.0.0.0:11434", queryChat);

        if (resultAnswer.exception().isEmpty()) {
            System.out.println("😛: " + resultAnswer.getAnswer().getMessage().getContent());
        } else {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }

    }
}
