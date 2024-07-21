package org.parakeetnest.parakeet4j;

import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import static org.parakeetnest.parakeet4j.completion.Completion.GenerateStream;

public class DemoGenerateStream
{
    public static void main( String[] args ) {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query("tinyllama", options)
                .setPrompt("Who is James T Kirk, and who is his best friend?");

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
