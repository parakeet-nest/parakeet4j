# Generate completion
> https://github.com/ollama/ollama/blob/main/docs/api.md#generate-a-completion
## Generate request

The simple completion can be used to generate a response for a given prompt with a provided model.

```Java
public class DemoGenerate
{
    public static void main( String[] args ) {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query()
                .setModel("tinyllama")
                .setPrompt("Who is James T Kirk, and who is his best friend?")
                .setOptions(options);

        Generate("http://localhost:11434", query,
                answer -> {
                    System.out.println("🙂: " + answer.getResponse());
                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                });
    }
}
```

### Alternative version

```Java
public class DemoGenerate
{
    public static void main( String[] args ) {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query()
                .setModel("tinyllama")
                .setPrompt("Who is James T Kirk, and who is his best friend?")
                .setOptions(options);

        var resultAnswer = Generate("http://localhost:11434", query);
        if (resultAnswer.exception().isEmpty()) {
            System.out.println(resultAnswer.exception().toString());
        }
    }
}
```

## Generate request with streaming

```Java
public class DemoGenerateStream
{
    public static void main( String[] args ) {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query("tinyllama", options)
                .setPrompt("Who is James T Kirk, and who is his best friend?");

        GenerateStream("http://0.0.0.0:11434", query,
                chunk -> {
                    // display the progress of completion chunk by chnunk
                    System.out.print(chunk.getResponse());
                    return null;
                },
                answer -> {
                    // At the end, display the entire answer
                    System.out.println();
                    System.out.println("--------------------------------------");
                    System.out.println("🙂: " + answer.getResponse());
                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                });
    }
}
```
> To stop the stream response, return an Exception with the `chunk ->{}` lambda.

### Alternative version

```java
public class DemoGenerateStream
{
    public static void main( String[] args ) {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query("tinyllama", options)
                .setPrompt("Who is James T Kirk, and who is his best friend?");

        var resultAnswer = GenerateStream("http://0.0.0.0:11434", query,
                chunk -> {
                    // display the progress of completion chunk by chnunk
                    System.out.print(chunk.getResponse());
                    //return new Exception("😡 shut up!"); //=> it stops the stream
                    return null;
                });

        // At the end, display the entire answer
        if(resultAnswer.exception().isEmpty()) {
            System.out.println();
            System.out.println("--------------------------------------");
            System.out.println("🙂: " + resultAnswer.getAnswer().getResponse());
        } else {
            System.out.println();
            System.out.println(resultAnswer.exception().toString());
        }
    }
}
```

## Completion with context

> The context can be used to keep a short conversational memory for the next completion.

```Java
public class DemoGenerateWithContext
{
    public static void main( String[] args ) {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query("tinyllama", options)
                .setPrompt("Who is James T Kirk?");

        var firstAnswer = new Answer();

        Generate("http://localhost:11434", query,
                answer -> {
                    System.out.println("🙂: " + answer.getResponse());
                    firstAnswer.setContext(answer.getContext());
                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                });

        System.out.println();
        System.out.println("--------------------------------------");

        Query nextQuery = new Query("tinyllama", options)
                .setContext(firstAnswer.getContext())
                .setPrompt("Who is his best friend?");

        Generate("http://localhost:11434", nextQuery,
                answer -> {
                    System.out.println("🙂: " + answer.getResponse());
                    firstAnswer.setContext(answer.getContext());
                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                });
    }
}
```