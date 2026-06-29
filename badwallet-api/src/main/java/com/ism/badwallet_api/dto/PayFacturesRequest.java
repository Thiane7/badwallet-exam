package com.ism.badwallet_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class PayFacturesRequest {
    private String phoneNumber;
    private String serviceName;
    private List<String> factureReferences;
}