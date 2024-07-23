package examples.embeddings;

import org.parakeetnest.parakeet4j.llm.*;

import java.util.List;

import static org.parakeetnest.parakeet4j.completion.Completion.Chat;
import static org.parakeetnest.parakeet4j.embeddings.Embeddings.CreateEmbedding;

public class DemoEmbeddingsCreation
{
    public static void main( String[] args )
    {
        var content = "The best Pizza of the world is the pineapple pizza.";
        Query4Embedding query4Embedding = new Query4Embedding("all-minilm",content);

        CreateEmbedding("http://0.0.0.0:11434", query4Embedding, "000",
                vectorRecord -> {
                    System.out.println(vectorRecord.getId() + ": " + vectorRecord.getPrompt());

                    for (double item : vectorRecord.getEmbedding()) {
                        System.out.print(item);
                        System.out.print(" ");
                    }
                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                });
    }
}
