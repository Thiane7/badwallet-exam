package com.ism.badwallet_api.controller;

import com.ism.badwallet_api.dto.WalletRequest;
import com.ism.badwallet_api.model.Wallet;
import com.ism.badwallet_api.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    // 1.1 Seeder
    @PostMapping("/seed")
    public ResponseEntity<String> seed(
            @RequestParam int numWallets,
            @RequestParam int eventsPerWallet) {
        return ResponseEntity.ok(walletService.seed(numWallets, eventsPerWallet));
    }

    // 1.2 Créer un nouveau portefeuille
    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody WalletRequest request) {
        return ResponseEntity.ok(walletService.createWallet(request));
    }
    // 1.3 Lister tous les portefeuilles (paginé)
    @GetMapping
    public ResponseEntity<Page<Wallet>> getAllWallets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(walletService.getAllWallets(PageRequest.of(page, size)));
    }
}