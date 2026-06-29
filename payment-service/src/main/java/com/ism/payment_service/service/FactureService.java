package com.ism.payment_service.service;

import com.ism.payment_service.model.Facture;
import com.ism.payment_service.repository.FactureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FactureService {

    private final FactureRepository factureRepository;

    public List<Facture> getFacturesDuMois(String walletCode) {
        return factureRepository.findByWalletCodeAndPayeeFalse(walletCode);
    }

    public List<Facture> getFacturesDuMoisParUnite(String walletCode, String unite) {
        return factureRepository.findByWalletCodeAndUniteAndPayeeFalse(walletCode, unite);
    }

    public List<Facture> getFacturesSurPeriode(String walletCode, LocalDate debut, LocalDate fin) {
        return factureRepository.findByWalletCodeAndPayeeFalseAndDateFactureBetween(
            walletCode, debut, fin
        );
    }

    public List<Facture> payerFactures(List<String> references) {
        List<Facture> factures = factureRepository.findByReferenceIn(references);
        factures.forEach(f -> f.setPayee(true));
        return factureRepository.saveAll(factures);
    }

    public List<Facture> payerFacturesDuMois(String walletCode, String unite) {
        List<Facture> factures = factureRepository
            .findByWalletCodeAndUniteAndPayeeFalse(walletCode, unite);
        factures.forEach(f -> f.setPayee(true));
        return factureRepository.saveAll(factures);
    }

    public void seeder(String walletCode) {
        for (int i = 1; i <= 3; i++) {
            Facture f1 = new Facture(null,
                "FAC-ISM-" + walletCode.replace("WLT-", "") + "-" + i,
                walletCode, "ISM", 5000.0, false, LocalDate.now());
            Facture f2 = new Facture(null,
                "FAC-WOYAFAL-" + walletCode.replace("WLT-", "") + "-" + i,
                walletCode, "WOYAFAL", 3000.0, false, LocalDate.now());
            factureRepository.save(f1);
            factureRepository.save(f2);
        }
    }
}