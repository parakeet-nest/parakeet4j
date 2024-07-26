package org.parakeetnest.parakeet4j.llm;

import io.vertx.core.json.JsonArray;

import java.util.List;

public class Tools {
    public static String GenerateContent(List<Tool> tools) {
        var jsonTools = new JsonArray(tools);
        return "[AVAILABLE_TOOLS] " + jsonTools.encodePrettily() + " [/AVAILABLE_TOOLS]";
    }

    public static String GenerateJsonToolsList(List<Tool> tools) {
        var jsonTools = new JsonArray(tools);
        return jsonTools.encodePrettily();
    }

    public static String GenerateInstructions(String instruction) {
        return "[INST] " + instruction + " [/INST]";
    }
}

