package com.sophia.numeralconverterrest.controller;

import com.sophia.numeralconverterrest.model.ConversionRequest;
import com.sophia.numeralconverterrest.model.ResponseBody;
import com.sophia.numeralconverterrest.service.ConversionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/converter")
public class ConversionRequestController {
    @Autowired
    ConversionRequestService conversionRequestService;


    public ConversionRequestController(ConversionRequestService conversionRequestService) {
    }

    @PostMapping
    public ResponseEntity<?> baseConverter(@RequestBody ConversionRequest request) {
        int sourceBase = request.getSourceBase();
        String num = request.getNumber();
        int targetBase = request.getTargetBase();
        if (sourceBase <= 0 || targetBase <= 0 || sourceBase > 36 || targetBase > 36) {
            return new ResponseEntity<>("Number must be within 1 to 36", HttpStatus.BAD_REQUEST);
        }
        String result = conversionRequestService.conversion(sourceBase, num, targetBase);
        if (result.isEmpty())
            return new ResponseEntity<>("Base out of range", HttpStatus.BAD_REQUEST);
        ResponseBody responseBody = new ResponseBody(result.substring(0, result.indexOf('=')), result);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
