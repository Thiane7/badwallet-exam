package com.ism.badwallet_api.service;

import com.ism.badwallet_api.dto.WalletRequest;
import com.ism.badwallet_api.model.Transaction;
import com.ism.badwallet_api.model.Wallet;
import com.ism.badwallet_api.repository.TransactionRepository;
import com.ism.badwallet_api.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public String seed(int numWallets, int eventsPerWallet) {
        Random random = new Random();
        for (int i = 1; i <= numWallets; i++) {
            String phone = "+22177000000" + i;
            String code = String.format("WLT-%07d", i);
            if (!walletRepository.existsByPhoneNumber(phone)) {
                Wallet w = new Wallet();
                w.setPhoneNumber(phone);
                w.setEmail("user" + i + "@gmail.com");
                w.setBalance(10000.0 + random.nextInt(90000));
                w.setCode(code);
                w.setCurrency("XOF");
                walletRepository.save(w);
            }
        }
        return numWallets + " wallets créés avec succès";
    }

    public Wallet createWallet(WalletRequest request) {
        Wallet wallet = new Wallet();
        wallet.setPhoneNumber(request.getPhoneNumber());
        wallet.setEmail(request.getEmail());
        wallet.setBalance(request.getInitialBalance());
        wallet.setCode(request.getCode());
        wallet.setCurrency(request.getCurrency());
        return walletRepository.save(wallet);
    }

    public Page<Wallet> getAllWallets(Pageable pageable) {
        return walletRepository.findAll(pageable);
    }

    public Wallet getWalletByPhone(String phoneNumber) {
        return walletRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Wallet non trouvé : " + phoneNumber));
    }

    public Double getBalance(String phoneNumber) {
        return getWalletByPhone(phoneNumber).getBalance();
    }

    public Wallet deposit(Long walletId, Double amount, String paymentMethod) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet non trouvé : " + walletId));
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
        Transaction t = new Transaction();
        t.setType("DEPOSIT");
        t.setAmount(amount);
        t.setFees(0.0);
        t.setDescription("Dépôt via " + paymentMethod);
        t.setReceiverPhone(wallet.getPhoneNumber());
        t.setWallet(wallet);
        transactionRepository.save(t);
        return wallet;
    }

    public Wallet withdraw(String phoneNumber, Double amount) {
        Wallet wallet = getWalletByPhone(phoneNumber);
        double fees = Math.min(amount * 0.01, 5000);
        double total = amount + fees;
        if (wallet.getBalance() < total) {
            throw new RuntimeException("Solde insuffisant");
        }
        wallet.setBalance(wallet.getBalance() - total);
        walletRepository.save(wallet);
        Transaction t = new Transaction();
        t.setType("WITHDRAWAL");
        t.setAmount(amount);
        t.setFees(fees);
        t.setDescription("Retrait avec frais de " + fees + " CFA");
        t.setSenderPhone(phoneNumber);
        t.setWallet(wallet);
        transactionRepository.save(t);
        return wallet;
    }

    public String transfer(String senderPhone, String receiverPhone, Double amount) {
        Wallet sender = getWalletByPhone(senderPhone);
        Wallet receiver = getWalletByPhone(receiverPhone);
        if (sender.getBalance() < amount) {
            throw new RuntimeException("Solde insuffisant");
        }
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        walletRepository.save(sender);
        walletRepository.save(receiver);
        Transaction t = new Transaction();
        t.setType("TRANSFER");
        t.setAmount(amount);
        t.setFees(0.0);
        t.setSenderPhone(senderPhone);
        t.setReceiverPhone(receiverPhone);
        t.setDescription("Transfert de " + senderPhone + " vers " + receiverPhone);
        t.setWallet(sender);
        transactionRepository.save(t);
        return "Transfert de " + amount + " XOF effectué avec succès";
    }

    public List<Transaction> getTransactions(String phoneNumber) {
        return transactionRepository
                .findBySenderPhoneOrReceiverPhoneOrderByCreatedAtDesc(phoneNumber, phoneNumber);
    }
}