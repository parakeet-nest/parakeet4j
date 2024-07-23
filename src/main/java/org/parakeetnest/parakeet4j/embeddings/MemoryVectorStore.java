package org.parakeetnest.parakeet4j.embeddings;

import org.parakeetnest.parakeet4j.llm.VectorRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryVectorStore {
    private final Map<String, VectorRecord> records;

    public MemoryVectorStore() {
        this.records = new HashMap<>();
    }

    public VectorRecord get(String id) {
        return records.get(id);
    }

    public List<VectorRecord> getAll() {
        return new ArrayList<>(records.values());
    }

    public VectorRecord save(VectorRecord vectorRecord) {
        records.put(vectorRecord.getId(), vectorRecord);
        return vectorRecord;
    }


    public VectorRecord searchMaxSimilarity(VectorRecord embeddingFromQuestion) {
        double maxDistance = -1.0;
        String selectedKeyRecord = null;

        for (Map.Entry<String, VectorRecord> entry : records.entrySet()) {
            String k = entry.getKey();
            VectorRecord v = entry.getValue();

            double distance = cosineDistance(embeddingFromQuestion.getEmbedding(), v.getEmbedding());
            if (distance > maxDistance) {
                maxDistance = distance;
                selectedKeyRecord = k;
            }
        }
        return records.get(selectedKeyRecord);
    }

    public List<VectorRecord> searchSimilarities(VectorRecord embeddingFromQuestion, double limit) {
        List<VectorRecord> recordsList = new ArrayList<>();

        for (VectorRecord v : records.values()) {

            double distance = cosineDistance(embeddingFromQuestion.getEmbedding(), v.getEmbedding());
            if (distance >= limit) {
                recordsList.add(v);
            }
        }

        return recordsList;
    }

    public static double dotProduct(double[] v1, double[] v2) {
        // Calculate the dot product of two vectors
        double sum = 0.0;
        for (int i = 0; i < v1.length; i++) {
            sum += v1[i] * v2[i];
        }
        return sum;
    }

    public static double cosineDistance(double[] v1, double[] v2) {
        // Calculate the cosine distance between two vectors
        double product = dotProduct(v1, v2);
        double norm1 = Math.sqrt(dotProduct(v1, v1));
        double norm2 = Math.sqrt(dotProduct(v2, v2));
        if (norm1 <= 0.0 || norm2 <= 0.0) {
            // Handle potential division by zero
            return 0.0;
        }
        return product / (norm1 * norm2);
    }
}
