package com.ism.badwallet_api.repository;

import com.ism.badwallet_api.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByPhoneNumber(String phoneNumber);

    Optional<Wallet> findByCode(String code);

    Page<Wallet> findAll(Pageable pageable);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByCode(String code);
}