package examples.embeddings;

import org.parakeetnest.parakeet4j.embeddings.MapDbVectorStore;
import org.parakeetnest.parakeet4j.llm.VectorRecord;

import static org.parakeetnest.parakeet4j.embeddings.Embeddings.CreateEmbedding;

public class DemoMapDbStoreEmbeddingRead
{
    public static void main( String[] args )
    {
        var store = new MapDbVectorStore("./vectors.db");
        VectorRecord vec = store.get("pineapple-pizza");

        System.out.println(vec.getId() + ": " + vec.getPrompt());
        for (double item : vec.getEmbedding()) {
            System.out.print(item);
            System.out.print(" ");
        }
    }
}
