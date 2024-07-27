package examples.prompts_samples;

import org.parakeetnest.parakeet4j.llm.Message;
import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import java.util.List;

import static org.parakeetnest.parakeet4j.completion.Completion.ChatStream;

public class DockerFile4GoLangWebApp
{
    public static void main( String[] args )
    {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2)
                .setRepeatPenalty(2.0)
                .setTopK(10)
                .setTopP(0.5);

        var systemContent = """
        As an expert in Go and Docker, generate a Dockerfile and Docker Compose file for a typical Go project.
        
        Regarding the Dockerfile:
        1. Use an official Go base image (I use go version '1.22.1')
        2. Use the /app working directory
        3. Copy the project source files
        4. Compile the Go application
        5. Create a lightweight final image for execution 
          - Use the /app working directory
          - Copy the binary in the /app directory
          - Copy the static assets (and directory structure) from /public to /app/public
        6. Start the Golang application
        
        Regarding the Compose file:
        The Golang application is using a Redis database
        1. Add a redis service with "redis-server" as name to the compose file.
          - The redis service is listening on the default port
        2. Add a webapp service 
          - The webapp service uses an environment variable (REDIS_URL)  to connect to the "redis-server" service
          - To set the value of REDIS_URL, use only the name of the redis service and the default redis port
          - The webapp service is listening on the 8080 HTTP port
          - the webapp service depends on the redis-server service
                
        Ensure the Dockerfile and the Compose file are well-commented and follows best practices. 
        Briefly explain each step after the Dockerfile and the Compose file.
        """;

        var userContent = """
        Here's the project structure:
        .
        ├── git.sh
        ├── go.mod
        ├── go.sum
        ├── init.sh
        ├── load-data
        │  ├── bulk_loading.sh
        │  └── data.txt
        ├── main.go
        ├── public
        │  ├── components
        │  │  ├── App.js
        │  │  └── Title.js
        │  ├── css
        │  │  ├── install-pico.md
        │  │  └── pico.min.css
        │  ├── index.html
        │  ├── info.txt
        │  └── js
        │     ├── install-preact.md
        │     ├── preact-htm.js
        │     └── update.js
        └── README.md
        """;


        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("user", userContent)
        );

        //Query queryChat = new Query("llama3.1:8b", options).setMessages(messages);
        // llama3.1:8b 🙂
        // granite-code:3b 😡
        // phi3:mini 😡
        // codeqwen 🙂
        Query queryChat = new Query("codeqwen", options).setMessages(messages);


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
