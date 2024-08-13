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

    /**
     * Converts a JSON array to a primitive double array.
     *
     * @param  jsonArray  the JSON array to convert
     * @return             the primitive double array representation of the JSON array
     */
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

    /**
     * Creates a vector record embedding from the given query and id.
     *
     * @param  ollamaUrl      the URL of the Ollama API
     * @param  query          the query to be embedded
     * @param  id             the id of the vector record
     * @param  onSuccess      handler for successful vector record creation
     * @param  onFailure      handler for failed vector record creation
     * @return                a ResultVectorRecord containing the created vector record or an error
     */
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

    /**
     * A description of the CreateEmbedding function.
     *
     * @param  ollamaUrl      description of the ollamaUrl parameter
     * @param  query          description of the query parameter
     * @param  id             description of the id parameter
     * @param  onSuccess      description of the onSuccess parameter
     * @param  onFailure      description of the onFailure parameter
     * @return                description of the return value
     */
    public static ResultVectorRecord CreateEmbedding(String ollamaUrl, Query4Embedding query, String id ,Handler<VectorRecord> onSuccess, Handler<Throwable> onFailure) {
        return createEmbedding(ollamaUrl, query, id, onSuccess, onFailure);
    }

    /**
     * Creates a vector record embedding from the given query and id.
     *
     * @param  ollamaUrl	the URL of the Ollama API
     * @param  query		the query to be embedded
     * @param  id			the id of the vector record
     * @return         	a ResultVectorRecord containing the created vector record or an error
     */
    public static ResultVectorRecord CreateEmbedding(String ollamaUrl, Query4Embedding query, String id) {
        return createEmbedding(ollamaUrl, query, id, v -> {}, e -> {});
    }

    /**
     * Generates a context string from an array of VectorRecord similarities.
     *
     * @param  similarities	an array of VectorRecord objects containing similarities
     * @return         	a string representing the context
     */
    public static String GenerateContextFromSimilarities(List<VectorRecord> similarities) {
        StringBuilder documentsContent = new StringBuilder("<context>\n");
        for (VectorRecord similarity : similarities) {
            documentsContent.append("<doc>").append(similarity.getPrompt()).append("</doc>\n");
        }
        documentsContent.append("</context>");
        return documentsContent.toString();
    }
    // TODO: to be tested

    /**
     * Generates a string containing the prompts of the given VectorRecord array.
     *
     * @param  similarities	the array of VectorRecord objects containing similarities
     * @return         		the string containing the prompts of the VectorRecord array
     */
    public static String GenerateContentFromSimilarities(List<VectorRecord> similarities) {
        String documentsContent = "";
        for (VectorRecord similarity : similarities) {
            documentsContent += String.format("%s\n", similarity.getPrompt());
        }
        return documentsContent;
    }
    // TODO: to be tested



}


