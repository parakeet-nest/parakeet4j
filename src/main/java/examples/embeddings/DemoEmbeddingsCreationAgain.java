package examples.embeddings;

import org.parakeetnest.parakeet4j.llm.Query4Embedding;

import static org.parakeetnest.parakeet4j.embeddings.Embeddings.CreateEmbedding;

public class DemoEmbeddingsCreationAgain
{
    public static void main( String[] args )
    {
        var content = "The best Pizza of the world is the pineapple pizza.";
        Query4Embedding query4Embedding = new Query4Embedding("all-minilm",content);

        var resVector = CreateEmbedding("http://0.0.0.0:11434", query4Embedding, "000");

        if (resVector.exception().isEmpty()) {
            System.out.println(resVector.getVectorRecord().getId() + ": " + resVector.getVectorRecord().getPrompt());

            for (double item : resVector.getVectorRecord().getEmbedding()) {
                System.out.print(item);
                System.out.print(" ");
            }
        } else {
            System.out.println("😡: " + resVector.exception().toString());
        }




    }
}
