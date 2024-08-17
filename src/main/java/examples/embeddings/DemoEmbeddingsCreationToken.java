package examples.embeddings;

import org.parakeetnest.parakeet4j.llm.Query4Embedding;

import static org.parakeetnest.parakeet4j.embeddings.Embeddings.CreateEmbedding;

public class DemoEmbeddingsCreationToken
{
    public static void main( String[] args )
    {
        var content = "The best Pizza of the world is the pineapple pizza.";
        Query4Embedding query4Embedding = new Query4Embedding("all-minilm",content);

        query4Embedding.setTokenHeaderName("X-TOKEN").setTokenHeaderValue("john snow");

        CreateEmbedding("https://ollamak33g.eu.loclx.io", query4Embedding, "pineapple-pizza",
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
