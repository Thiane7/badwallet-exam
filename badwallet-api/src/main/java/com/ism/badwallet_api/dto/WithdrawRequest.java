package com.ism.badwallet_api.dto;

import lombok.Data;

@Data
public class WithdrawRequest {
    private String phoneNumber;
    private Double amount;
}