package examples.rag_chunker;

import org.parakeetnest.parakeet4j.content.Content;
import org.parakeetnest.parakeet4j.embeddings.Embeddings;
import org.parakeetnest.parakeet4j.embeddings.MemoryVectorStore;
import org.parakeetnest.parakeet4j.llm.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.parakeetnest.parakeet4j.completion.Completion.ChatStream;
import static org.parakeetnest.parakeet4j.embeddings.Embeddings.CreateEmbedding;

public class DemoRag
{
    public static void main( String[] args ) throws IOException {

        var embeddingsModel = "all-minilm:33m"; // This model is for the embeddings of the documents
        var smallChatModel = "qwen2:0.5b";  // This model is for the chat completion

        var store = new MemoryVectorStore();

        String filePath = "./src/main/java/examples/rag_chunker/chronicles.md";
        var content = new String(Files.readAllBytes(Paths.get(filePath)));

        var chunks = Content.ChunkText(content, 500, 200);

        // Create embeddings for each chunk
        AtomicInteger index = new AtomicInteger();
        for (String chunk : chunks) {
            Query4Embedding query4Embedding = new Query4Embedding(embeddingsModel,chunk);
            CreateEmbedding("http://0.0.0.0:11434", query4Embedding,  Integer.toString(index.get()),
                    vectorRecord -> {
                        store.save(vectorRecord);
                        index.getAndIncrement();
                    },
                    err -> {
                        System.out.println("😡: " + err.getMessage());
                    });
        }

        var systemContent = """
        You are the dungeon master,
        expert at interpreting and answering questions based on provided sources.
        Using only the provided context, answer the user's question 
        according to the best of your ability using only the resources provided.
        Be verbose!""";

        var userContent = "Who are the monsters of Chronicles of Aethelgard?";
        Query4Embedding query4Embedding = new Query4Embedding(embeddingsModel,userContent);

        // Create embedding with the user question:
        var resVector = CreateEmbedding("http://0.0.0.0:11434", query4Embedding, "question");

        var documentsContent = "";
        // Search the similarities
        if(resVector.exception().isEmpty()) {
            var similarities = store.searchSimilarities(resVector.getVectorRecord(), 0.3);

            documentsContent = Embeddings.GenerateContentFromSimilarities(similarities);

            System.out.println(documentsContent);
            System.out.println("----------------------------------------");
            System.out.println("similarities: " + similarities.size());
            System.out.println("----------------------------------------");


        } else {
            System.out.println("😡: " + resVector.exception().toString());
            System.exit(1);
        }

        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2)
                .setRepeatPenalty(2.0)
                .setTopK(10)
                .setTopP(0.5);

        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("system", documentsContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query(smallChatModel, options, messages);

        var resChat = ChatStream("http://0.0.0.0:11434", queryChat,
                            chunk -> {
                                System.out.print(chunk.getMessage().getContent());
                                return null;
                            });

        if(resChat.exception().isPresent()) {
            System.out.println("😡: " + resChat.exception().toString());
            System.exit(1);
        }

    }
}
