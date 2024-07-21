package org.parakeetnest.parakeet4j;

import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import static org.parakeetnest.parakeet4j.completion.Completion.Generate;

public class DemoGenerate
{
    public static void main( String[] args ) {

        Options options = new Options();
        options.setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query();
        query.setModel("tinyllama")
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
