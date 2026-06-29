
package com.ism.payment_service.controller;

import com.ism.payment_service.model.Facture;
import com.ism.payment_service.service.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;

    @GetMapping("/{walletCode}/current")
    public ResponseEntity<List<Facture>> getFacturesDuMois(
            @PathVariable String walletCode,
            @RequestParam(required = false) String unite) {
        if (unite != null) {
            return ResponseEntity.ok(
                factureService.getFacturesDuMoisParUnite(walletCode, unite)
            );
        }
        return ResponseEntity.ok(factureService.getFacturesDuMois(walletCode));
    }

    @GetMapping("/{walletCode}/periode")
    public ResponseEntity<List<Facture>> getFacturesSurPeriode(
            @PathVariable String walletCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(
            factureService.getFacturesSurPeriode(walletCode, debut, fin)
        );
    }

    @PostMapping("/payer")
    public ResponseEntity<List<Facture>> payerFactures(
            @RequestBody List<String> references) {
        return ResponseEntity.ok(factureService.payerFactures(references));
    }

    @PostMapping("/{walletCode}/payer-mois")
    public ResponseEntity<List<Facture>> payerFacturesDuMois(
            @PathVariable String walletCode,
            @RequestParam String unite) {
        return ResponseEntity.ok(
            factureService.payerFacturesDuMois(walletCode, unite)
        );
    }

    @PostMapping("/seed/{walletCode}")
    public ResponseEntity<String> seed(@PathVariable String walletCode) {
        factureService.seeder(walletCode);
        return ResponseEntity.ok("Factures créées pour " + walletCode);
    }
}