package examples.generate;

import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import static org.parakeetnest.parakeet4j.completion.Completion.Generate;

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

                    System.out.println("📦" +  answer.toJsonString());

                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                });
    }
}
