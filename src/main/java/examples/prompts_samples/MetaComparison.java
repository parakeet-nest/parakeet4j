package examples.prompts_samples;

import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import static org.parakeetnest.parakeet4j.completion.Completion.GenerateStream;
import static org.parakeetnest.parakeet4j.prompt.Meta.Comparison;

public class MetaComparison
{
    public static void main( String[] args ) {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query("llama3", options)
                .setPrompt(Comparison("Compare Rust and Golang"));

        var resultAnswer = GenerateStream("http://0.0.0.0:11434", query,
                chunk -> {
                    System.out.print(chunk.getResponse());
                    return null;
                });

        if(resultAnswer.exception().isPresent()) {
            System.out.println();
            System.out.println(resultAnswer.exception().toString());
        }
    }
}
