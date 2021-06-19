package com.sophia.numeralconverterrest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseBody {
    private String result;
    private String calculation;
}
