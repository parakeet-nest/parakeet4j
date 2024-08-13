package org.parakeetnest.parakeet4j.embeddings;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.parakeetnest.parakeet4j.llm.VectorRecord;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class MapDbVectorStore {

    private DB db;
    private String  bucketName = "embeddings-store-bucket";

    public MapDbVectorStore(String dbPath) {
        db = DBMaker.fileDB(dbPath).transactionEnable().make();
    }

    private static double[] jsonArrayToDoubleArray(JsonArray jsonArray) {
        if (jsonArray == null) {
            return new double[0];
        }

        double[] result = new double[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            // Use getDouble() to get the value as a Double, then convert to primitive double
            result[i] = jsonArray.getDouble(i);
        }
        return result;
    }

    public VectorRecord get(String id) {
        ConcurrentMap map = db.hashMap(bucketName).createOrOpen();
        String record = map.get(id).toString();
        db.close();

        JsonObject jsonRecord = new JsonObject(record);
        var embedding = jsonArrayToDoubleArray(jsonRecord.getJsonArray("embedding"));
        VectorRecord vectorRecord = new VectorRecord();
        vectorRecord.setPrompt(jsonRecord.getString("prompt"));
        vectorRecord.setId(id);
        vectorRecord.setEmbedding(embedding);

        return vectorRecord;
    }

    public List<VectorRecord> getAll() {
        List<VectorRecord> recordsList = new ArrayList<>();
        ConcurrentMap map = db.hashMap(bucketName).createOrOpen();


        map.forEach((key, record) -> {
            JsonObject jsonRecord = new JsonObject(String.valueOf(record));
            var embedding = jsonArrayToDoubleArray(jsonRecord.getJsonArray("embedding"));
            VectorRecord vectorRecord = new VectorRecord();
            vectorRecord.setPrompt(jsonRecord.getString("prompt"));
            vectorRecord.setId((String) key);
            vectorRecord.setEmbedding(embedding);
            recordsList.add(vectorRecord);
        });

        db.close();
        return recordsList;
    }

    public VectorRecord save(VectorRecord vectorRecord) {
        String vecJsonStr = Json.encode(vectorRecord);
        ConcurrentMap map = db.hashMap(bucketName).createOrOpen();
        map.put(vectorRecord.getId(), vecJsonStr);
        db.commit();
        //db.close();
        return vectorRecord;
    }

    public void close() {
        db.close();
    }

    public VectorRecord searchMaxSimilarity(VectorRecord embeddingFromQuestion) {
        double maxDistance = -1.0;
        //String selectedKeyRecord = null;
        VectorRecord selectedRecord = null;

        List<VectorRecord> records = getAll();

        for(VectorRecord entry : records) {
            double distance = cosineDistance(embeddingFromQuestion.getEmbedding(), entry.getEmbedding());
            if (distance > maxDistance) {
                maxDistance = distance;
                selectedRecord = entry;
            }
        }
        return selectedRecord;
    }

    public List<VectorRecord> searchSimilarities(VectorRecord embeddingFromQuestion, double limit) {
        List<VectorRecord> recordsList = new ArrayList<>();
        List<VectorRecord> records = getAll();

        for (VectorRecord v : records) {
            double distance = cosineDistance(embeddingFromQuestion.getEmbedding(), v.getEmbedding());
            if (distance >= limit) {
                v.setCosineDistance(distance);
                recordsList.add(v);
            }
        }
        return recordsList;
    }

    public List<VectorRecord> searchTopNSimilarities(VectorRecord embeddingFromQuestion, double limit, int max) {
        List<VectorRecord> recordsList = searchSimilarities(embeddingFromQuestion, limit);
        return getTopNVectorRecords(recordsList, max);
    }

    // Public?
    private static List<VectorRecord> getTopNVectorRecords(List<VectorRecord> records, int max) {
        // Sort the records list in descending order based on CosineDistance
        Collections.sort(records, new Comparator<VectorRecord>() {
            @Override
            public int compare(VectorRecord vr1, VectorRecord vr2) {
                return Double.compare(vr2.getCosineDistance(), vr1.getCosineDistance());
            }
        });

        // Return the first max records or all if less than max
        if (records.size() < max) {
            return records;
        }
        return records.subList(0, max);
    }

    private static double dotProduct(double[] v1, double[] v2) {
        // Calculate the dot product of two vectors
        double sum = 0.0;
        for (int i = 0; i < v1.length; i++) {
            sum += v1[i] * v2[i];
        }
        return sum;
    }

    private static double cosineDistance(double[] v1, double[] v2) {
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
