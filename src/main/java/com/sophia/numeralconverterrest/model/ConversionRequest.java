package com.sophia.numeralconverterrest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConversionRequest {
    @NonNull
    private int sourceBase;
    @NonNull
    private  String number;
    @NonNull
    private int targetBase;
}
