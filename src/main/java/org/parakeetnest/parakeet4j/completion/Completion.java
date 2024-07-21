package org.parakeetnest.parakeet4j.completion;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.parakeetnest.parakeet4j.llm.Answer;
import org.parakeetnest.parakeet4j.llm.Message;
import org.parakeetnest.parakeet4j.llm.Query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;

public class Completion {

    private static void completion(String ollamaUrl, String kindOfCompletion, Query query, Handler<Answer> onSuccess, Handler<Throwable> onFailure) {

        query.setStream(false);

        try {
            // Create HTTP client
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            JsonObject jsonOllamaPayload = new JsonObject(Json.encode(query));

            // Create HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(ollamaUrl+"/api/"+kindOfCompletion))
                    .header("Content-Type", "application/json; charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonOllamaPayload.toString()))
                    .build();

            // Send the request and get the answer
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Handle the response
            if (response.statusCode() == 200) {
                JsonObject jsonAnswer = new JsonObject(response.body());
                Answer answer = new Answer();
                answer.setModel(jsonAnswer.getString("model"));

                String dateTimeString = jsonAnswer.getString("created_at");
                // Parse the date time string to an Instant object
                Instant instant = Instant.parse(dateTimeString);
                // Convert the Instant object to a LocalDateTime object using the system default time zone
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                answer.setCreatedAt(localDateTime);

                // if /generate or /chat
                switch (kindOfCompletion) {
                    case "generate":
                        answer.setResponse(jsonAnswer.getString("response"));

                        JsonArray jsonContext = jsonAnswer.getJsonArray("context");
                        ArrayList<Integer> intList = new ArrayList<Integer>();
                        for (Object value : jsonContext) {
                            intList.add((int) value);
                        }
                        int[] intArray = new int[intList.size()];
                        Arrays.setAll(intArray, intList::get);
                        answer.setContext(intArray);

                        break;
                    case "chat":
                        //System.out.println("🟢" + jsonAnswer.getJsonObject("message"));
                        //JsonObject jsonMessage = new JsonObject(jsonAnswer.getString("message"));
                        JsonObject jsonMessage = jsonAnswer.getJsonObject("message");
                        Message msg = new Message();
                        msg.setRole(jsonMessage.getString("role"));
                        msg.setContent(jsonMessage.getString("content"));
                        answer.setMessage(msg);
                        break;
                    default:
                        //foo;
                }

                answer.setDone(jsonAnswer.getBoolean("done"));
                // TODO: to implement: done_reason
                answer.setTotalDuration(jsonAnswer.getLong("total_duration"));
                answer.setLoadDuration(jsonAnswer.getInteger("load_duration"));
                answer.setPromptEvalCount(jsonAnswer.getInteger("prompt_eval_count"));
                answer.setPromptEvalDuration(jsonAnswer.getInteger("prompt_eval_duration"));
                answer.setEvalCount(jsonAnswer.getInteger("eval_count"));
                answer.setEvalDuration(jsonAnswer.getLong("eval_duration"));

                onSuccess.handle(answer);


            } else {
                //response.statusCode())
                onFailure.handle(new Error(response.body()));
            }

        } catch (MalformedURLException e) {
            // System.out.println("URL is invalid : " + e.getMessage());
            onFailure.handle(e);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            onFailure.handle(e);
            //throw new RuntimeException(e);
        }

    }

    private static void processJsonStream(java.io.InputStream inputStream, String kindOfCompletion, ChunkHandler<Answer, Error> onChunk, Handler<Answer> onSuccess) throws Exception {
        Answer answerOnSuccess = new Answer();
        answerOnSuccess.setResponse("");
        answerOnSuccess.setMessage(new Message("",""));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Each line is a JSON String

                JsonObject jsonAnswer = new JsonObject(line);

                Answer answer = new Answer();
                answer.setModel(jsonAnswer.getString("model"));

                String dateTimeString = jsonAnswer.getString("created_at");
                // Parse the date time string to an Instant object
                Instant instant = Instant.parse(dateTimeString);
                // Convert the Instant object to a LocalDateTime object using the system default time zone
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                answer.setCreatedAt(localDateTime);

                // if /generate or /chat
                switch (kindOfCompletion) {
                    case "generate":
                        answer.setResponse(jsonAnswer.getString("response"));
                        break;
                    case "chat":
                        JsonObject jsonMessage = jsonAnswer.getJsonObject("message");
                        Message msg = new Message();
                        msg.setRole(jsonMessage.getString("role"));
                        msg.setContent(jsonMessage.getString("content"));
                        answer.setMessage(msg);
                        break;
                    default:
                        //foo;
                }


                answer.setDone(jsonAnswer.getBoolean("done"));

                // Streaming is terminated
                if(jsonAnswer.getBoolean("done")) {

                    // if /generate or /chat
                    switch (kindOfCompletion) {
                        case "generate":
                            answerOnSuccess.setContext(answer.getContext());
                            break;
                        case "chat":
                            //foo
                            break;
                        default:
                            //foo;
                    }

                    answerOnSuccess.setModel(answer.getModel());
                    answerOnSuccess.setCreatedAt(answer.getCreatedAt());
                    // TODO: to implement: done_reason
                    answerOnSuccess.setTotalDuration(answer.getTotalDuration());
                    answerOnSuccess.setLoadDuration(answer.getLoadDuration());
                    answerOnSuccess.setPromptEvalCount(answer.getPromptEvalCount());
                    answerOnSuccess.setPromptEvalDuration(answer.getPromptEvalDuration());
                    answerOnSuccess.setEvalCount(answer.getEvalCount());
                    answerOnSuccess.setEvalDuration(answer.getEvalDuration());

                    onSuccess.handle(answerOnSuccess);

                } else {
                    // if /generate or /chat
                    switch (kindOfCompletion) {
                        case "generate":
                            answerOnSuccess.setResponse(answerOnSuccess.getResponse()+answer.getResponse());
                            break;
                        case "chat":
                            JsonObject jsonMessage = jsonAnswer.getJsonObject("message");
                            Message msg = new Message();
                            msg.setRole(jsonMessage.getString("role"));

                            msg.setContent(answerOnSuccess.getMessage().getContent() + jsonMessage.getString("content"));

                            answerOnSuccess.setMessage(msg);
                            break;
                        default:
                            //foo;
                    }

                }

                Error err = onChunk.handle(answer);
                if(err != null)  {
                    reader.reset();
                };
            }
        }
    }



    private static void completionStream(String ollamaUrl, String kindOfCompletion, Query query, ChunkHandler<Answer, Error> onChunk, Handler<Answer> onSuccess, Handler<Throwable> onFailure) {

        query.setStream(true);

        try {

            // Create HTTP client
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            JsonObject jsonOllamaPayload = new JsonObject(Json.encode(query));

            // Create HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(ollamaUrl+"/api/"+kindOfCompletion))
                    .header("Content-Type", "application/json; charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonOllamaPayload.toString()))
                    .build();

            // Send the request and get the stream answer
            //HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            // Handle the response
            if (response.statusCode() == 200) {

                processJsonStream(response.body(), kindOfCompletion, onChunk, onSuccess);

            } else {
                //response.statusCode())
                onFailure.handle(new Error(response.body().toString()));
            }

        } catch (MalformedURLException e) {
            // System.out.println("URL is invalid : " + e.getMessage());
            onFailure.handle(e);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            onFailure.handle(e);
            //throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public static void Generate(String ollamaUrl, Query query, Handler<Answer> onSuccess, Handler<Throwable> onFailure) {
        completion(ollamaUrl, "generate", query, onSuccess, onFailure);
    }

    public static void GenerateStream(String ollamaUrl, Query query, ChunkHandler<Answer, Error> onChunk, Handler<Answer> onSuccess, Handler<Throwable> onFailure) {
        completionStream(ollamaUrl, "generate", query, onChunk, onSuccess, onFailure);
    }

    public static void Chat(String ollamaUrl, Query query, Handler<Answer> onSuccess, Handler<Throwable> onFailure) {
        completion(ollamaUrl, "chat", query, onSuccess, onFailure);
    }

    public static void ChatStream(String ollamaUrl, Query query, ChunkHandler<Answer, Error> onChunk, Handler<Answer> onSuccess, Handler<Throwable> onFailure) {
        completionStream(ollamaUrl, "chat", query, onChunk, onSuccess, onFailure);
    }

}