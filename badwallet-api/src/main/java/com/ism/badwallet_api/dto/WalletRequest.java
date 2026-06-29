package com.ism.badwallet_api.dto;

import lombok.Data;

@Data
public class WalletRequest {
    private String phoneNumber;
    private String email;
    private Double initialBalance;
    private String code;
    private String currency;
}