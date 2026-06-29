package com.ism.payment_service.repository;

import com.ism.payment_service.model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

    List<Facture> findByWalletCodeAndPayeeFalse(String walletCode);

    List<Facture> findByWalletCodeAndUniteAndPayeeFalse(String walletCode, String unite);

    List<Facture> findByWalletCodeAndPayeeFalseAndDateFactureBetween(
            String walletCode, LocalDate debut, LocalDate fin);

    List<Facture> findByReferenceIn(List<String> references);
}