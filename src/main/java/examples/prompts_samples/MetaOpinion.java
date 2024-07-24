package examples.prompts_samples;

import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import static org.parakeetnest.parakeet4j.completion.Completion.GenerateStream;
import static org.parakeetnest.parakeet4j.prompt.Meta.Opinion;
import static org.parakeetnest.parakeet4j.prompt.Meta.ProsAndCons;

public class MetaOpinion
{
    public static void main( String[] args ) {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query("llama3", options)
                .setPrompt(Opinion("For the development of CLI, which is better: Golang or Rust?"));

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
