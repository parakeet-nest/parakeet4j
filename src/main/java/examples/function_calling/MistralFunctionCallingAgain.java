package examples.function_calling;

import io.vertx.core.json.JsonArray;
import org.parakeetnest.parakeet4j.llm.*;

import java.util.List;
import java.util.Map;

import static org.parakeetnest.parakeet4j.completion.Completion.Chat;
import static org.parakeetnest.parakeet4j.llm.Tools.GenerateContent;
import static org.parakeetnest.parakeet4j.llm.Tools.GenerateInstructions;

public class MistralFunctionCallingAgain
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
            System.out.println("😛: " + resultAnswer.getAnswer().getMessage().getContent().trim());
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
            System.out.println("😛: " + resultAnswer.getAnswer().getMessage().getContent().trim());
        } else {
            System.out.println("😡: " + resultAnswer.exception().toString());
        }

    }
}
