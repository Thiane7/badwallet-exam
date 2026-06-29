package com.ism.badwallet_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/external")
@RequiredArgsConstructor
public class ExternalFactureController {

    private final WebClient.Builder webClientBuilder;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    @GetMapping("/factures/{walletCode}/current")
    public ResponseEntity<Object> getFacturesDuMois(
            @PathVariable String walletCode,
            @RequestParam(required = false) String unite) {
        String url = paymentServiceUrl + "/api/factures/" + walletCode + "/current";
        if (unite != null) url += "?unite=" + unite;
        Object result = webClientBuilder.build()
                .get().uri(url).retrieve()
                .bodyToMono(Object.class).block();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/factures/{walletCode}/periode")
    public ResponseEntity<Object> getFacturesSurPeriode(
            @PathVariable String walletCode,
            @RequestParam String debut,
            @RequestParam String fin) {
        String url = paymentServiceUrl + "/api/factures/" + walletCode
                + "/periode?debut=" + debut + "&fin=" + fin;
        Object result = webClientBuilder.build()
                .get().uri(url).retrieve()
                .bodyToMono(Object.class).block();
        return ResponseEntity.ok(result);
    }
}