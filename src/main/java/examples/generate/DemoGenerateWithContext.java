package examples.generate;

import org.parakeetnest.parakeet4j.llm.Answer;
import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import java.util.ArrayList;
import java.util.List;

import static org.parakeetnest.parakeet4j.completion.Completion.Generate;

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
