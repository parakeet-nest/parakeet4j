# Function Calling

[How does function calling with tools really work?](https://www.youtube.com/watch?v=IdPdwQdM9lA)

Function calling is a technique that allows the model to interact with external functions or APIs. It enables the LLM to recognize when a specific function should be called based on the user's input, and to generate the appropriate parameters for that function. This capability enhances the model's ability to perform specific tasks, access external data, or trigger actions in other systems, effectively bridging the gap between natural language processing and practical, real-world applications.

But there's nothing magical about it, you'll have to code the function calls. The LLM can't do it on its own.

## Preamble: JSON output

You can generate completion with a JSON output.

```java
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

        Query queryChat = new Query("phi3:mini", options)
                .setMessages(messages)
                .setRaw(true)
                .setFormat("json");

        var resultAnswer = Chat("http://0.0.0.0:11434", queryChat);

        if (resultAnswer.exception().isEmpty()) {
            System.out.println(resultAnswer.getAnswer().getMessage().getContent());
        } else {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }
    }
}
```
The result will look like this:

```json
{
  "scientific_name": "Gallus gallus domesticus",
  "main_species": "chicken",
  "average_length": 45,
  "average_weight": 2.3,
  "average_lifespan": 8,
  "countries": ["United States of America", "India"]
}
```

## Use a model that supports function calling

[Mistral](https://ollama.com/library/mistral) (since the version 0.3) supports function calling with Ollama’s raw mode.

- You need to define a prompt like this: `[AVAILABLE_TOOLS] list of tools [/AVAILABLE_TOOLS][INST] instruction [/INST]`
- Use the `raw` mode and the `json` output format of Ollama
- Set the temperature to `0.0`

```java
public class MistralFunctionCalling
{
    public static void main( String[] args )
    {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2)
                .setRepeatPenalty(2.0);

        var toolsContent = """
        [AVAILABLE_TOOLS]
        {
            "type": "function",
            "function": {
                "name": "addNumbers",
                "description": "Make an addition of the two given numbers",
                "parameters": {
                    "type": "object",
                    "properties": {
                        "a": {
                            "type": "number",
                            "description": "first operand"
                        },
                        "b": {
                            "type": "number",
                            "description": "second operand"
                        }
                    },
                    "required": ["a", "b"]
                }
            }
        }        
        [/AVAILABLE_TOOLS]""";

        userContent = "[INST] add 2 and 40 [/INST]";

        List<Message> messages = List.of(
                new Message("system", toolsContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query("mistral:7b", options)
                .setRaw(true)
                .setFormat("json")
                .setMessages(messages);

        var resultAnswer = Chat("http://0.0.0.0:11434", queryChat);

        if (resultAnswer.exception().isEmpty()) {
            System.out.println(resultAnswer.getAnswer().getMessage().getContent().trim());
        } else {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }
    }
}
```
The result will look like this:
```json
{
    "name": "addNumbers",
    "arguments": {
        "a": 2,
        "b": 40
    }
}
```

If you don't like using JSON strings, PArakett4J comes with some helpers:

```java
public class MistralFunctionCalling
{
    public static void main( String[] args )
    {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2)
                .setRepeatPenalty(2.0);

        var helloFunction = new Function("hello",
                new Parameters("object",
                        Map.of("name",
                                new Property("string", "The name of the person")
                        ),
                        List.of("name")
                ),
                "Say hello to a given person with his name"
        );

        var addNumbersFunction = new Function("addNumbers",
                new Parameters("object",
                        Map.of("a",
                                new Property("number", "first operand"),
                                "b",
                                new Property("number", "second operand")
                        ),
                        List.of("a", "b")
                ),
                "Make an addition of the two given numbers"
        );

        var toolsList = List.of(
                new Tool("function", helloFunction),
                new Tool("function", addNumbersFunction)
        );


        List<Message> messages = List.of(
                new Message("system", GenerateContent(toolsList)),
                new Message("user", GenerateInstructions("say \"hello\" to Bob"))
        );

        Query queryChat = new Query("mistral:7b", options)
                .setRaw(true)
                .setFormat("json")
                .setMessages(messages);

        var resultAnswer = Chat("http://0.0.0.0:11434", queryChat);

        if (resultAnswer.exception().isEmpty()) {
            System.out.println(resultAnswer.getAnswer().getMessage().getContent().trim());
        } else {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }
        
        messages = List.of(
                new Message("system", GenerateContent(toolsList)),
                new Message("user", GenerateInstructions("add 2 and 40"))
        );
        queryChat.setMessages(messages);

        resultAnswer = Chat("http://0.0.0.0:11434", queryChat);

        if (resultAnswer.exception().isEmpty()) {
            System.out.println(resultAnswer.getAnswer().getMessage().getContent().trim());
        } else {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }

    }
}
```
The result will look like this:
```json
{
  "name" : "hello",
  "arguments" : {
    "name" : "Bob"
  }
}
```
```json
{
  "name" : "addNumbers",
  "arguments" : {
    "a" : 2,
    "b" : 40
  }
}
```

## How to do function calling with a model that does not support it?

You can use the same principle, but you have to add some guidance and instructions:

1. Start with an instruction like this:
    ```
    You have access to the following tools:
    ```
2. Generate the list of tools
3. Add a kind of guidance like this:
    ```
    If the question of the user matched the description of a tool, the tool will be called.
    To call a tool, respond with a JSON object with the following structure:
    {
        "name": <name of the called tool>,
        "arguments": {
            <name of the argument>: <value of the argument>
        }
    }
    
    search the name of the tool in the list of tools with the Name field
    ```
4. Add the instruction for calling a function

This is an example with the very small model `qwen2:0.5b`:
```java
public class QwenFunctionCallingAgain
{
    public static void main( String[] args )
    {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2)
                .setRepeatPenalty(2.0)
                .setSeed(123); // 🤔

        var systemContentInstructions = """
        If the question of the user matched the description of a tool, the tool will be called.
        To call a tool, respond with a JSON object with the following structure:
        {
            "name": <name of the called tool>,
            "arguments": {
                <name of the argument>: <value of the argument>
            }
        }
        
        search the name of the tool in the list of tools with the Name field
	    """;

        var helloFunction = new Function("hello",
                new Parameters("object",
                        Map.of("name",
                                new Property("string", "The name of the person")
                        ),
                        List.of("name")
                ),
                "Say hello to a given person with his name"
        );

        var addNumbersFunction = new Function("addNumbers",
                new Parameters("object",
                        Map.of("a",
                                new Property("number", "first operand"),
                                "b",
                                new Property("number", "second operand")
                        ),
                        List.of("a", "b")
                ),
                "Make an addition of the two given numbers"
        );

        var toolsList = List.of(
                new Tool("function", helloFunction),
                new Tool("function", addNumbersFunction)
        );

        List<Message> messages = List.of(
                new Message("system", "You have access to the following tools:"),
                new Message("system", GenerateContent(toolsList)),
                new Message("system", systemContentInstructions),
                new Message("user", GenerateInstructions("say \"hello\" to Bob"))
        );
        
        Query queryChat = new Query("qwen2:0.5b", options)
                .setRaw(true)
                .setFormat("json")
                .setMessages(messages);

        var resultAnswer = Chat("http://0.0.0.0:11434", queryChat);

        if (resultAnswer.exception().isEmpty()) {
            System.out.println("😛: " + resultAnswer.getAnswer().getMessage().getContent().trim());
        } else {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }


        messages = List.of(
                new Message("system", "You have access to the following tools:"),
                new Message("system", GenerateContent(toolsList)),
                new Message("system", systemContentInstructions),
                new Message("user", GenerateInstructions("add 2 and 40"))
        );
        queryChat.setMessages(messages);

        resultAnswer = Chat("http://0.0.0.0:11434", queryChat);

        if (resultAnswer.exception().isEmpty()) {
            System.out.println("😛: " + resultAnswer.getAnswer().getMessage().getContent().trim());
        } else {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }

    }
}
```