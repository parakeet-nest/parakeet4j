package examples.function_calling;

import org.parakeetnest.parakeet4j.llm.Message;
import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import java.util.List;

import static org.parakeetnest.parakeet4j.completion.Completion.Chat;

public class FakeFunctionCalling
{
    public static void main( String[] args )
    {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2)
                .setRepeatPenalty(2.0);

        var toolsContent = """
        You have access to the following tools:
        BEGIN LIST
        Name: hello,
        Description: Say hello to a given person with his name
        Parameters: value of name

        Name: addNumbers,
        Description: Make an addition of the two given numbers,
        Parameters: [a, b]
        END LIST
        
        If the question of the user matched the description of a tool, the tool will be called.
        
        To call a tool, respond with a JSON object with the following structure:\s
        {
          "tool": <name of the called tool>,
          "parameters": <parameters for the tool matching the above Parameters list>
        }
        
        search the name of the tool in the list of tools with the Name field                
        """;


        var systemContent = """
        You are a helpful AI assistant. The user will enter a sentence.
        If the sentence is near the description of a tool, the assistant will call the tool.
        Output the results in JSON format and trim the spaces of the sentence.
        """;

        var userContent = "add 5 and 40";

        List<Message> messages = List.of(
                new Message("system", toolsContent),
                new Message("system", systemContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query("phi3:mini", options)
                .setRaw(true)
                .setFormat("json")
                .setMessages(messages);

        var resultAnswer = Chat("http://0.0.0.0:11434", queryChat);

        if (resultAnswer.exception().isEmpty()) {
            System.out.println("😛: " + resultAnswer.getAnswer().getMessage().getContent());
        } else {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }

        userContent = "say \"hello\" to Bob";
        messages = List.of(
                new Message("system", toolsContent),
                new Message("system", systemContent),
                new Message("user", userContent)
        );
        queryChat.setMessages(messages);

        resultAnswer = Chat("http://0.0.0.0:11434", queryChat);

        if (resultAnswer.exception().isEmpty()) {
            System.out.println("😛: " + resultAnswer.getAnswer().getMessage().getContent());
        } else {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }

    }
}
