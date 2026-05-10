package com.email.writer.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class EmailGeneratorService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public EmailGeneratorService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String generateEmailReply(EmailRequest emailRequest) {
        try {
            // Build the prompt
            String prompt = buildPrompt(emailRequest);

            // Craft the request body
            Map<String, Object> requestBody = Map.of(
                    "contents", new Object[]{
                            Map.of("parts", new Object[]{
                                    Map.of("text", prompt)
                            })
                    }
            );

            // Make API call to Gemini
            String response = webClient.post()
                    .uri(geminiApiUrl + geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .flatMap(errorBody -> {
                                        System.err.println("API Error Response: " + errorBody);
                                        return Mono.error(new RuntimeException("Gemini API Error: " + errorBody));
                                    })
                    )
                    .bodyToMono(String.class)
                    .block();

            // Extract and return the response
            return extractResponseContent(response);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating email: " + e.getMessage();
        }
    }

    private String extractResponseContent(String response) {
        try {
            if (response == null || response.isEmpty()) {
                return "Error: Empty response from API";
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);

            // Navigate through the response structure
            JsonNode candidates = rootNode.path("candidates");
            if (candidates.isEmpty() || !candidates.isArray()) {
                return "Error: No candidates found in response";
            }

            JsonNode firstCandidate = candidates.get(0);
            JsonNode content = firstCandidate.path("content");
            JsonNode parts = content.path("parts");

            if (parts.isEmpty() || !parts.isArray()) {
                return "Error: No parts found in response";
            }

            String generatedText = parts.get(0).path("text").asText();
            return generatedText.isEmpty() ? "Error: Empty text in response" : generatedText;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing API response: " + e.getMessage();
        }
    }

    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate a professional email reply for the following email content. ");
        prompt.append("Please don't generate a subject line. ");

        if (emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
            prompt.append("Use a ").append(emailRequest.getTone()).append(" tone. ");
        } else {
            prompt.append("Use a professional and courteous tone. ");
        }

        prompt.append("\n\nOriginal email:\n");
        prompt.append(emailRequest.getEmailContent());
        prompt.append("\n\nGenerated reply:\n");

        return prompt.toString();
    }
}