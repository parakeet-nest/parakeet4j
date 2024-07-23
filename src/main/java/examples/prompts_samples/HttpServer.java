package examples.prompts_samples;

import org.parakeetnest.parakeet4j.llm.Message;
import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import java.util.List;

import static org.parakeetnest.parakeet4j.completion.Completion.ChatStream;

public class HttpServer
{
    public static void main( String[] args )
    {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        var systemContent = """
        As an experienced Go developer, create a Go program that sets up an HTTP server with the provided requirements.
        Please provide a complete, well-structured, and commented Go code that implements this HTTP server.
        After the code, briefly explain the key components and how they work together.
        """;

        var userContent = """
        Requirements:
        1. Use the standard "net/http" package.
        2. Create a route to serve static files from a "static" directory.
        3. Create a route "/api/info" that returns JSON data with some basic information.
        4. The server should listen on port 8080.
        5. Include proper error handling and logging.
        
        The JSON data returned by the "/api/info" route should include:
        - The server's name
        - The current time
        - A version number
        """;

        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query("phi3:mini", options).setMessages(messages);

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
