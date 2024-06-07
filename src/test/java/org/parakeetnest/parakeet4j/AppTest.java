package org.parakeetnest.parakeet4j;

import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

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
