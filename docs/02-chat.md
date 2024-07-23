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