# Chat completion
> https://github.com/ollama/ollama/blob/main/docs/api.md#generate-a-chat-completion

## Chat request

```java
public class DemoChat
{
    public static void main( String[] args )
    {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        var systemContent = "You are a useful AI agent, expert with the Star Trek franchise.";
        var userContent = "Who is Jean-Luc Picard?";

        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query("tinyllama", options).setMessages(messages);

        Chat("http://0.0.0.0:11434", queryChat,
                answer -> {
                    System.out.println("😛: " + answer.getMessage().getContent());
                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                });

    }
}
```

### Alternative version

```java
public class DemoChat
{
    public static void main( String[] args )
    {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        var systemContent = "You are a useful AI agent, expert with the Star Trek franchise.";
        var userContent = "Who is Jean-Luc Picard?";

        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query("tinyllama", options).setMessages(messages);

        var resultAnswer = Chat("http://0.0.0.0:11434", queryChat);

        if (resultAnswer.exception().isEmpty()) {
            System.out.println("😛: " + resultAnswer.getAnswer().getMessage().getContent());
        } else {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }

    }
}
```

## Chat request with streaming

```java
public class DemoChatStream
{
    public static void main( String[] args )
    {
        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        var systemContent = "You are a useful AI agent, expert with the Star Trek franchise.";
        var userContent = "Who is Jean-Luc Picard?";

        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query("tinyllama", options).setMessages(messages);
        
        ChatStream("http://0.0.0.0:11434", queryChat,
                chunk -> {
                    System.out.print(chunk.getMessage().getContent());
                    //return new Exception("😡"); //=> it stops the stream
                    return null;
                },
                answer -> {
                    System.out.println();
                    System.out.println("--------------------------------------");
                    System.out.println("😛: " + answer.getMessage().getContent());
                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                });
    }
}
```

### Alternative version

```java
public class DemoChatStream
{
    public static void main( String[] args )
    {
        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        var systemContent = "You are a useful AI agent, expert with the Star Trek franchise.";
        var userContent = "Who is Jean-Luc Picard?";

        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query("tinyllama", options).setMessages(messages);

        var resultAnswer = ChatStream("http://0.0.0.0:11434", queryChat,
                chunk -> {
                    System.out.print(chunk.getMessage().getContent());
                    //return new Exception("😡"); //=> it stops the stream
                    return null;
                });

        if(resultAnswer.exception().isEmpty()) {
            System.out.println();
            System.out.println("--------------------------------------");
            System.out.println("😛: " + resultAnswer.getAnswer().getMessage().getContent());
        } else {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }
    }
}
```

## Chat request with conversational memory

```java
public class DemoChatStreamWithMemory
{
    public static void main( String[] args )
    {
        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        var conversation = new MemoryMessages();

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
```

## Verbose mode

> Use this setter: `.setVerbose(true)`
```java
Options options = new Options()
        .setTemperature(0.0)
        .setRepeatLastN(2)
        .setVerbose(true);
```

output:
```raw
[llm/query]:
{
  "model" : "tinyllama",
  "messages" : [ {
    "role" : "system",
    "content" : "You are a useful AI agent, expert with the Star Trek franchise.",
    "toolCalls" : null
  }, {
    "role" : "user",
    "content" : "Who is Jean-Luc Picard?",
    "toolCalls" : null
  } ],
  "options" : {
    "repeatLastN" : 2,
    "temperature" : 0.0,
    "seed" : 0,
    "repeatPenalty" : 0.0,
    "stop" : null,
    "numKeep" : 0,
    "numPredict" : 0,
    "topK" : 0,
    "topP" : 0.0,
    "tfsZ" : 0.0,
    "typicalP" : 0.0,
    "presencePenalty" : 0.0,
    "frequencyPenalty" : 0.0,
    "mirostat" : 0,
    "mirostatTau" : 0.0,
    "mirostatEta" : 0.0,
    "penalizeNewline" : false,
    "verbose" : true
  },
  "stream" : true,
  "prompt" : null,
  "context" : null,
  "format" : null,
  "keepAlive" : false,
  "raw" : false,
  "system" : null,
  "template" : null,
  "tools" : null
}
```

```raw
[llm/completion]:
{
  "model" : "tinyllama",
  "message" : {
    "role" : "assistant",
    "content" : "Jean-Luc Picard is a fictional character from the Star Trek franchise, played by Patrick Stewart in the original series and movies. He was the captain of the USS Enterprise NCC-1701 during its first five seasons, and later served as the second captain of the USS Enterprise NCC-1701-D during the show's final season. Picard is known for his intelligence, leadership skills, and dedication to the Star Trek universe.",
    "toolCalls" : null
  },
  "done" : false,
  "response" : "",
  "context" : null,
  "createdAt" : "2024-08-17T06:33:16.841942",
  "totalDuration" : 563225541,
  "loadDuration" : 15516083,
  "promptEvalCount" : 52,
  "promptEvalDuration" : 45375000,
  "evalCount" : 104,
  "evalDuration" : 499293000
}
```
