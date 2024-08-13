package examples.embeddings;

import org.parakeetnest.parakeet4j.embeddings.MapDbVectorStore;
import org.parakeetnest.parakeet4j.embeddings.MemoryVectorStore;
import org.parakeetnest.parakeet4j.llm.Query4Embedding;

import java.util.concurrent.atomic.AtomicInteger;

import static org.parakeetnest.parakeet4j.embeddings.Embeddings.CreateEmbedding;

public class DemoVectorStoreMapDbEmbeddingsCreation
{
    public static void main( String[] args )
    {

        String[] docs = {"""
        Michael Burnham is the main character on the Star Trek series, Discovery.  
        She's a human raised on the logical planet Vulcan by Spock's father.  
        Burnham is intelligent and struggles to balance her human emotions with Vulcan logic.  
        She's become a Starfleet captain known for her determination and problem-solving skills.
        Originally played by actress Sonequa Martin-Green""",
                """
        James T. Kirk, also known as Captain Kirk, is a fictional character from the Star Trek franchise.  
        He's the iconic captain of the starship USS Enterprise, 
        boldly exploring the galaxy with his crew.  
        Originally played by actor William Shatner, 
        Kirk has appeared in TV series, movies, and other media.""",
                """
        Jean-Luc Picard is a fictional character in the Star Trek franchise.
        He's most famous for being the captain of the USS Enterprise-D,
        a starship exploring the galaxy in the 24th century.
        Picard is known for his diplomacy, intelligence, and strong moral compass.
        He's been portrayed by actor Patrick Stewart.""",
                """
        Lieutenant Philippe Charrière, known as the **Silent Sentinel** of the USS Discovery, 
        is the enigmatic programming genius whose codes safeguard the ship's secrets and operations. 
        His swift problem-solving skills are as legendary as the mysterious aura that surrounds him. 
        Charrière, a man of few words, speaks the language of machines with unrivaled fluency, 
        making him the crew's unsung guardian in the cosmos. His best friend is Spiderman from the Marvel Cinematic Universe."""
        };

        var store = new MapDbVectorStore("./star.trek.vectors.db");

        AtomicInteger index = new AtomicInteger();
        // Create embedding for each chunk of docs
        for (String doc : docs) {
            Query4Embedding query4Embedding = new Query4Embedding("all-minilm",doc);

            var resVector = CreateEmbedding("http://0.0.0.0:11434", query4Embedding, Integer.toString(index.get()));

            if (resVector.exception().isEmpty()) {
                System.out.println(resVector.getVectorRecord().getId() + ": " + resVector.getVectorRecord().getPrompt());

                store.save(resVector.getVectorRecord());
                index.getAndIncrement();

            } else {
                System.out.println("😡: " + resVector.exception().toString());
            }
        }
        store.close();
    }
}
