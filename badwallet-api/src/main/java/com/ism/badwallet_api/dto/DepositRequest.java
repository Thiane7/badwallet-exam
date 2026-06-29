package com.ism.badwallet_api.dto;

import lombok.Data;

@Data
public class DepositRequest {
    private Double amount;
    private String paymentMethod;
}