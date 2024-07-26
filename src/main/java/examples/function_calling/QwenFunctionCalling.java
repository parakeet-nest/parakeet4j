package examples.function_calling;

import org.parakeetnest.parakeet4j.llm.*;

import java.util.List;
import java.util.Map;

import static org.parakeetnest.parakeet4j.completion.Completion.Chat;
import static org.parakeetnest.parakeet4j.llm.Tools.GenerateContent;
import static org.parakeetnest.parakeet4j.llm.Tools.GenerateInstructions;

// 👋🔴 this is an experiment, it does not work entirely
public class QwenFunctionCalling
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

        var toolsContent = """
        [AVAILABLE_TOOLS]
        {
            "type": "function",
            "function": {
                "name": "hello",
                "description": "Say hello to a given person with his name",
                "parameters": {
                    "type": "object",
                    "properties": {
                        "name": {
                            "type": "string",
                            "description": "The name of the person"
                        }
                    },
                    "required": ["name"]
                }
            }
        },
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

        var userContent = "[INST] say \"hello\" to Bob [/INST]";

        List<Message> messages = List.of(
                new Message("system", "You have access to the following tools:"),
                new Message("system", toolsContent),
                new Message("system", systemContentInstructions),
                new Message("user", userContent)
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

        userContent = "[INST] add 2 and 40 [/INST]";

        messages = List.of(
                new Message("system", toolsContent),
                new Message("system", systemContentInstructions),
                new Message("user", userContent)
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
