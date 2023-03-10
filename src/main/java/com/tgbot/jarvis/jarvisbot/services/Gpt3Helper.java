package com.tgbot.jarvis.jarvisbot.services;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author Vlad
 */
@Service
public class Gpt3Helper {
    //key for server
    private static final String API_KEY = "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";

    public static String generateResponse(String prompt) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("https://api.openai.com/v1/completions")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .header("OpenAI-Organization", "org-slWC5RnPqNPBRukkCTvhFJW1")
                .body(new JSONObject()
                        .put("model", "text-davinci-003")
                        .put("prompt", prompt)
                        .put("max_tokens", 1024)
                        .put("n", 1)
                        .put("stop", "\\n")
                        .put("temperature", 0.5)
                )
                .asJson();

        String responseText = response.getBody().getObject().getJSONArray("choices").getJSONObject(0).getString("text");
        return responseText.strip();
    }
}
