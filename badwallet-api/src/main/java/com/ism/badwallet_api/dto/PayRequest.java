package com.ism.badwallet_api.dto;

import lombok.Data;

@Data
public class PayRequest {
    private String phoneNumber;
    private String serviceName;
    private Double amount;
}