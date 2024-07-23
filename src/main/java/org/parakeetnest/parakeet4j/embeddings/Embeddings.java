package org.parakeetnest.parakeet4j.embeddings;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.parakeetnest.parakeet4j.llm.Answer;
import org.parakeetnest.parakeet4j.llm.Query;
import org.parakeetnest.parakeet4j.llm.Query4Embedding;
import org.parakeetnest.parakeet4j.llm.VectorRecord;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Embeddings {

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


    private static ResultVectorRecord createEmbedding(String ollamaUrl, Query4Embedding query, String id ,Handler<VectorRecord> onSuccess, Handler<Throwable> onFailure) {
        try {
            // Create HTTP client
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            JsonObject jsonOllamaPayload = new JsonObject(Json.encode(query));

            // Create HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(ollamaUrl+"/api/embeddings"))
                    .header("Content-Type", "application/json; charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonOllamaPayload.toString()))
                    .build();

            // Send the request and get the answer
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonAnswer = new JsonObject(response.body());

                var embedding = jsonArrayToDoubleArray(jsonAnswer.getJsonArray("embedding"));

                VectorRecord vectorRecord = new VectorRecord();

                vectorRecord.setPrompt(query.getPrompt());
                vectorRecord.setId(id);

                vectorRecord.setEmbedding(embedding);

                onSuccess.handle(vectorRecord);
                return new ResultVectorRecord(vectorRecord, null);


            } else {
                //response.statusCode())
                Exception e = new Exception(response.body());
                onFailure.handle(e);
                return new ResultVectorRecord(null, e);

            }


        } catch (Exception e) {
            onFailure.handle(e);
            return new ResultVectorRecord(null, e);
        }
    }

    public static ResultVectorRecord CreateEmbedding(String ollamaUrl, Query4Embedding query, String id ,Handler<VectorRecord> onSuccess, Handler<Throwable> onFailure) {
        return createEmbedding(ollamaUrl, query, id, onSuccess, onFailure);
    }

    public static ResultVectorRecord CreateEmbedding(String ollamaUrl, Query4Embedding query, String id) {
        return createEmbedding(ollamaUrl, query, id, v -> {}, e -> {});
    }
}








// func CreateEmbedding(ollamaUrl string, query llm.Query4Embedding, id string) (llm.VectorRecord, error) {