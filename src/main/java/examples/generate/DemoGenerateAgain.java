package examples.generate;

import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import static org.parakeetnest.parakeet4j.completion.Completion.Generate;

public class DemoGenerateAgain
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
            System.out.println(resultAnswer.getAnswer().getResponse());
        }
    }
}
