package org.parakeetnest.parakeet4j;

import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.Test;
import org.parakeetnest.parakeet4j.llm.Options;
import org.parakeetnest.parakeet4j.llm.Query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.parakeetnest.parakeet4j.completion.Completion.Generate;
import static org.parakeetnest.parakeet4j.completion.Completion.GenerateStream;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */

    @Test
    public void shouldAnswerWithTrue()
    {
        AbstractStringAssert<?> equalTo = assertThat("hello").isEqualTo("hello");

    }

    @Test
    public void shouldContainSpock() {
        Options options = new Options();
        options.setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query();
        query.setModel("tinyllama")
                .setPrompt("Who is James T Kirk, and who is his best friend?")
                .setOptions(options);

        Generate("http://0.0.0.0:11434", query,
                answer -> {
                    System.out.println("🙂: " + answer.getResponse());
                    assertThat(answer.getResponse().contains("Spock")).isTrue();
                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                    assertThat(err).isNull(); // will fail
                });
    }

    @Test
    public void shouldContainSpockWithStream() {
        Options options = new Options();
        options.setTemperature(0.0)
                .setRepeatLastN(2);

        Query query = new Query();
        query.setModel("tinyllama")
                .setPrompt("Who is James T Kirk, and who is his best friend?")
                .setOptions(options);

        GenerateStream("http://0.0.0.0:11434", query,
                chunk -> {
                    System.out.print(".");
                    return null;
                },
                answer -> {
                    System.out.println("🙂: " + answer.getResponse());
                    assertThat(answer.getResponse().contains("Spock")).isTrue();
                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                    assertThat(err).isNull(); // will fail
                });
    }
}
