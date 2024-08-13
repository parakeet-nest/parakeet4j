package examples.embeddings;

import org.parakeetnest.parakeet4j.embeddings.MapDbVectorStore;
import org.parakeetnest.parakeet4j.llm.Query4Embedding;
import org.parakeetnest.parakeet4j.llm.VectorRecord;

import java.util.concurrent.atomic.AtomicInteger;

import static org.parakeetnest.parakeet4j.embeddings.Embeddings.CreateEmbedding;

public class DemoVectorStoreMapDbEmbeddingsRead
{
    public static void main( String[] args )
    {
        var store = new MapDbVectorStore("./star.trek.vectors.db");

        var userContent = "Who is Philippe Charrière?";
        Query4Embedding query4Embedding = new Query4Embedding("all-minilm",userContent);
        System.out.println("----------------------------------------");

        // Create embedding with the user question:
        CreateEmbedding("http://0.0.0.0:11434", query4Embedding, "question",
                vectorRecord -> {
                    System.out.println(vectorRecord.getId() + ": " + vectorRecord.getPrompt());

                    // Search the similarities
                    var similarities = store.searchTopNSimilarities(vectorRecord, 0.4, 1);

                    // Display the list of similarities
                    for (VectorRecord record : similarities) {
                        System.out.println(record.getCosineDistance());
                        System.out.println(record.getPrompt());
                        System.out.println("----------------------------------------");
                    }
                },
                err -> {
                    System.out.println("😡: " + err.getMessage());
                });
    }
}
