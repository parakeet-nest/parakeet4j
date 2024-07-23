package org.parakeetnest.parakeet4j;

import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import static org.parakeetnest.parakeet4j.completion.Completion.GenerateStream;

public class DemoGenerateStreamAgain
{
    public static void main( String[] args ) {

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query("tinyllama", options)
                .setPrompt("Who is James T Kirk, and who is his best friend?");

        var resultAnswer = GenerateStream("http://0.0.0.0:11434", query,
                chunk -> {
                    System.out.print(chunk.getResponse());
                    //return new Exception("😡 shut up!"); //=> it stops the stream
                    return null;
                });

        if(resultAnswer.getException() == null) {
            System.out.println();
            System.out.println("--------------------------------------");
            System.out.println("🙂: " + resultAnswer.getAnswer().getResponse());
        } else {
            System.out.println();
            System.out.println(resultAnswer.getException().getMessage());
        }
    }
}
