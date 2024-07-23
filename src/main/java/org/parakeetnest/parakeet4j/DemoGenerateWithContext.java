package org.parakeetnest.parakeet4j;

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

        Query query = new Query()
                .setModel("tinyllama")
                .setPrompt("Who is James T Kirk?")
                .setOptions(options);

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

        Query nextQuery = new Query()
                .setModel("tinyllama")
                .setPrompt("Who is his best friend?")
                .setContext(firstAnswer.getContext())
                .setOptions(options);

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
