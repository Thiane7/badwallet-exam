package com.ism.badwallet_api.controller;

import com.ism.badwallet_api.dto.WalletRequest;
import com.ism.badwallet_api.model.Wallet;
import com.ism.badwallet_api.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ism.badwallet_api.dto.DepositRequest;
import com.ism.badwallet_api.dto.PayRequest;
import com.ism.badwallet_api.dto.TransferRequest;
import com.ism.badwallet_api.dto.PayRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ism.badwallet_api.dto.WithdrawRequest;

import com.ism.badwallet_api.dto.TransferRequest;
import com.ism.badwallet_api.dto.PayFacturesRequest;
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
    // 1.4 Consulter un portefeuille par numéro de téléphone
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Wallet> getWalletByPhone(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(walletService.getWalletByPhone(phoneNumber));
    }

    // 1.5 Consulter uniquement le solde
    @GetMapping("/{phoneNumber}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(walletService.getBalance(phoneNumber));
    }

    // 1.6 Effectuer un dépôt
    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<Wallet> deposit(
            @PathVariable Long walletId,
            @RequestBody DepositRequest request) {
        return ResponseEntity.ok(walletService.deposit(walletId, request.getAmount(), request.getPaymentMethod()));
    }

    // 1.7 Effectuer un retrait
    @PostMapping("/withdraw")
    public ResponseEntity<Wallet> withdraw(@RequestBody WithdrawRequest request) {
        return ResponseEntity.ok(walletService.withdraw(request.getPhoneNumber(), request.getAmount()));
    }

    // 1.8 Effectuer un transfert
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        return ResponseEntity.ok(walletService.transfer(
            request.getSenderPhone(),
            request.getReceiverPhone(),
            request.getAmount()
        ));
    }
    // 1.9 Payer une facture du mois en cours
    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody PayRequest request) {
        return ResponseEntity.ok(walletService.payFactureDuMois(
            request.getPhoneNumber(),
            request.getServiceName(),
            request.getAmount()
        ));
    }

    // 1.10 Payer des factures spécifiques
    @PostMapping("/pay-factures")
    public ResponseEntity<String> payFactures(@RequestBody PayFacturesRequest request) {
        return ResponseEntity.ok(walletService.payFacturesSpecifiques(
            request.getPhoneNumber(),
            request.getServiceName(),
            request.getFactureReferences()
        ));
    }






    
}