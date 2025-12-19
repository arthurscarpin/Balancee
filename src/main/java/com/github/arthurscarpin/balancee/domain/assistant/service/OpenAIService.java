package com.github.arthurscarpin.balancee.domain.assistant.service;

import com.github.arthurscarpin.balancee.domain.transaction.dto.TransactionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OpenAIService {

    private final WebClient webClient;

    private final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");

    private final String SYSTEM_PROMPT = """
                You are an AI financial insights assistant embedded in a personal finance application.
                
                Your responsibility is to transform pre-aggregated financial data into clear,
                accurate, and helpful insights for end users.
                
                You must always follow these rules:
                - Speak directly to the user in simple, non-technical language
                - Be concise, objective, and supportive
                - Do NOT perform calculations or data aggregation
                - Do NOT invent or assume missing data
                - Do NOT mention internal systems, databases, SQL, models, or algorithms
                - Focus on trends, changes, recurring expenses, and actionable opportunities
                - Avoid judgmental, moralizing, or alarming language
                - Prioritize clarity and usefulness over verbosity
                
                Your output must strictly follow the format requested in the user message.
                """;

    private final String USER_PROMPT = """
                Using the aggregated financial data below, generate personalized financial insights
                for the user.
                
                Output requirements:
                - Generate 3 to 5 insights
                - Each insight must be a single, concise sentence
                - Use bullet points
                - Use percentages and currency values when relevant
                
                If the data is insufficient to generate a specific insight, omit it.
                
                User financial summary:
                %s
                """;

    public OpenAIService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> sendPrompt(List<TransactionResponse> dto) {
        String transactions = dto.stream()
                .map(t -> String.format("Date: %s, Description: %s, Amount: %.2f, Category: %s",
                        t.getDate(), t.getDescription(), t.getAmount(), t.getCategoryType()))
                .collect(Collectors.joining("\n"));
        Map<String, Object> requestBody = Map.of(
                "model","gpt-5.2",
                "input", List.of(
                        Map.of("role", "system", "content", SYSTEM_PROMPT),
                        Map.of("role", "user", "content", USER_PROMPT.formatted(transactions))
                )
        );
        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + OPENAI_API_KEY)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var output = (List<Map<String, Object>>) response.get("output");
                    if (output != null && !output.isEmpty()) {
                        var content = (List<Map<String, Object>>) output.get(0).get("content");
                        return content.get(0).get("text").toString();
                    }
                    return "Nothing recipe went generate.";
                });
    }
}
