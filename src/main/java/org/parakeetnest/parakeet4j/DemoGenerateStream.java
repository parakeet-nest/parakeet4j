package org.parakeetnest.parakeet4j;

import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import static org.parakeetnest.parakeet4j.completion.Completion.GenerateStream;

public class DemoGenerateStream
{
    public static void main( String[] args ) {

        Options options = new Options();
        options.setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query();
        query.setModel("tinyllama")
                .setPrompt("Who is James T Kirk, and who is his best friend?")
                .setOptions(options);


        GenerateStream("http://0.0.0.0:11434", query,
                chunk -> {
                    System.out.print(chunk.getResponse());
                    //return new Error("😡"); //=> it stops the stream
                    return null;
                },
                answer -> {
                    System.out.println();
                    System.out.println("--------------------------------------");
                    System.out.println("🙂: " + answer.getResponse());
                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                });
    }
}
