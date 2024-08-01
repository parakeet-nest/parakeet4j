package examples.prompts_samples;

import org.parakeetnest.parakeet4j.llm.Message;
import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import java.util.List;

import static org.parakeetnest.parakeet4j.completion.Completion.ChatStream;

public class DockerFile4NodejsWebApp
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
        As an expert in NodeJS and Docker generate Dockerfiles and Docker Compose file for a typical NodeJS project. 
        The project has 2 services: `api` and `web`. Then you will generate two Dockerfiles:
        - one for the api service
        - one for the web service
                
        Regarding the api Dockerfile:
        1. Use an official nodejs slim base image (I use node version '21.0.0')
        2. Use the /app working directory
        3. Copy the all the project source files
        4. Don't use npm, use yarn to install the dependencies application
        5. Start the nodejs application
        
        Regarding the web Dockerfile:
        1. Use an official nodejs slim base image (I use node version '21.0.0')
        2. Use the /app working directory
        3. Copy the all the project source files
        4. Don't use npm, use yarn to install the dependencies application
        5. Start the nodejs application
        
        Regarding the Compose file:
        The api service is using a Redis database
        1. Add a redis service with "redis-server" as name to the compose file.
          - The redis service is listening on the default port
        2. Add the api service 
          - The api service uses an environment variable (REDIS_URL)  to connect to the "redis-server" service
          - To set the value of REDIS_URL, use only the name of the redis service and the default redis port
          - The host port is 6060 and the container port is 8080.
          - The api service depends on the redis-server service
          - The api service is using this environment variable FRONT_URL=http://localhost:7070 to allow the front to connect to the API (CORS, Cross-Origin Resource Sharing)
        3. Add the web service (and name it webapp)
          - The webapp service depends on api service
          - The host port is 7070 and the container port is 8080.
          - The webapp service is using this environment variable API_URL=http://localhost:6060

        Ensure the Dockerfile and the Compose file are well-commented and follows best practices. 
        Briefly explain each step after the Dockerfile and the Compose file.
        """;

        var userContent = """
        Here's the project structure:
        .
        ├── api
        │  ├── index.js
        │  └── package.json
        ├── compose.yaml
        ├── README.md
        └── web
           ├── index.js
           ├── package.json
           ├── public
           │  └── css
           │     └── bulma.min.css
           └── templates
              └── index.ejs
        """;


        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query("gemma2:2b", options).setMessages(messages);
        // llama3.1:8b 🙂
        // gemma2:2b 🙂
        // codeqwen 🙂
        //Query queryChat = new Query("codeqwen", options).setMessages(messages);


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
