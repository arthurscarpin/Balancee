package com.github.arthurscarpin.balancee.ai;

import com.github.arthurscarpin.balancee.domain.transaction.TransactionResponseDTO;
import com.github.arthurscarpin.balancee.domain.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/assistance-insights")
public class AssistantInsightsController {

    private final OpenAIService openAIService;

    private final TransactionService transactionService;

    public AssistantInsightsController(OpenAIService openAIService, TransactionService transactionService) {
        this.openAIService = openAIService;
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions/{id}")
    @Operation(summary = "Generate insights by User ID.", description = "Retrieves a insights by its unique User ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Insights by ID."),
            @ApiResponse(responseCode = "404", description = "Insights not found.")
    })
    public Mono<ResponseEntity<String>> generateInsights(@PathVariable Long id) {
        List<TransactionResponseDTO> transactionsDto = transactionService.findByUserId(id);
        if (transactionsDto.isEmpty()) {
            return Mono.just(ResponseEntity.notFound().build());
        }
        return openAIService.sendPrompt(transactionsDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}
