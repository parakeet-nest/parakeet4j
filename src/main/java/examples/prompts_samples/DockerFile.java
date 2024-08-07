package examples.prompts_samples;

import org.parakeetnest.parakeet4j.llm.Message;
import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import java.util.List;

import static org.parakeetnest.parakeet4j.completion.Completion.Chat;
import static org.parakeetnest.parakeet4j.completion.Completion.ChatStream;

public class DockerFile
{
    public static void main( String[] args )
    {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        var systemContent = """
        As an expert in Go and Docker, generate a Dockerfile for a typical Go project. The Dockerfile should:
        
        1. Use an official Go base image
        2. Copy the project source files
        3. Compile the Go application
        4. Create a lightweight final image for execution
        
        Then, generate a compose file for the project.
        
        Ensure the Dockerfile is well-commented and follows best practices. Briefly explain each step after the Dockerfile.
        """;

        var userContent = """
        Here's the project structure:
        /
        ├── main.go
        ├── go.mod
        └── go.sum                
        """;


        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query("llama3.1:8b", options).setMessages(messages);
        //Query queryChat = new Query("llama3", options).setMessages(messages);

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
