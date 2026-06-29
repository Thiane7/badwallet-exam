package com.ism.badwallet_api.controller;

import com.ism.badwallet_api.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}