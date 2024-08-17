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
  "messages" : null,
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
  "stream" : false,
  "prompt" : "Who is James T Kirk, and who is his best friend?",
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
  "message" : null,
  "done" : true,
  "response" : "James T Kirk is the main protagonist of the Star Trek franchise, played by William Shatner in the original series (1966-1968) and later by Leonard Nimoy in the subsequent films. He is a captain of the USS Enterprise, a starship that serves as the flagship of the United Federation of Planets.\n\nJames T Kirk's best friend is Spock, played by Leonard Nimoy in the original series and later by Zachary Quinto in the rebooted films. Spock is a Vulcan scientist who has a deep understanding of science and technology, but also possesses a strong sense of logic and reason. He is often at odds with Kirk's more practical approach to problem-solving, but ultimately proves to be a valuable ally in the face of danger.",
  "context" : [ 529, 29989, 5205, 29989, 29958, 13, 3492, 526, 263, 8444, 319, 29902, 20255, 29889, 2, 29871, 13, 29966, 29989, 1792, 29989, 29958, 13, 22110, 338, 5011, 323, 26424, 29892, 322, 1058, 338, 670, 1900, 5121, 29973, 2, 29871, 13, 29966, 29989, 465, 22137, 29989, 29958, 13, 29470, 323, 26424, 338, 278, 1667, 15572, 391, 310, 278, 7828, 6479, 29895, 23272, 895, 29892, 5318, 491, 4667, 1383, 271, 1089, 297, 278, 2441, 3652, 313, 29896, 29929, 29953, 29953, 29899, 29896, 29929, 29953, 29947, 29897, 322, 2678, 491, 26921, 15981, 4346, 29891, 297, 278, 15352, 12298, 29889, 940, 338, 263, 15315, 310, 278, 17676, 9041, 7734, 29892, 263, 10819, 4034, 393, 19700, 408, 278, 13449, 4034, 310, 278, 3303, 20438, 310, 20540, 29879, 29889, 13, 13, 29470, 323, 26424, 29915, 29879, 1900, 5121, 338, 24674, 384, 29892, 5318, 491, 26921, 15981, 4346, 29891, 297, 278, 2441, 3652, 322, 2678, 491, 29103, 653, 29223, 29877, 297, 278, 22538, 287, 12298, 29889, 24674, 384, 338, 263, 478, 352, 3068, 9638, 391, 1058, 756, 263, 6483, 23400, 13161, 8497, 310, 10466, 322, 15483, 29892, 541, 884, 22592, 267, 263, 4549, 4060, 310, 5900, 322, 2769, 29889, 940, 338, 4049, 472, 7736, 29879, 411, 26424, 29915, 29879, 901, 15031, 2948, 304, 1108, 29899, 2929, 1747, 29892, 541, 18973, 28281, 304, 367, 263, 21114, 599, 29891, 297, 278, 3700, 310, 9703, 29889 ],
  "createdAt" : "2024-08-17T06:39:54.754173",
  "totalDuration" : 1187899208,
  "loadDuration" : 280386875,
  "promptEvalCount" : 47,
  "promptEvalDuration" : 41295000,
  "evalCount" : 178,
  "evalDuration" : 865478000
}
```


