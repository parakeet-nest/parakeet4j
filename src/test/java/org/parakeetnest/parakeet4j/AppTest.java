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

}
