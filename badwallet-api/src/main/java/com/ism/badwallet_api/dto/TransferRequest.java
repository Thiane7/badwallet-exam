package com.ism.badwallet_api.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private String senderPhone;
    private String receiverPhone;
    private Double amount;
}