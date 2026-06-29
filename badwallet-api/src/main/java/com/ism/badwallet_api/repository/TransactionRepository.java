package com.ism.badwallet_api.repository;

import com.ism.badwallet_api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByWalletPhoneNumberOrderByCreatedAtDesc(String phoneNumber);

    List<Transaction> findBySenderPhoneOrReceiverPhoneOrderByCreatedAtDesc(
        String senderPhone, String receiverPhone
    );
}