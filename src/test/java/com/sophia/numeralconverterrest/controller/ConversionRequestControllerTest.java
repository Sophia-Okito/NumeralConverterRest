package com.sophia.numeralconverterrest.controller;

import com.sophia.numeralconverterrest.model.ConversionRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ConversionRequestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void baseConverter() {
        ConversionRequest valid = new ConversionRequest(2, "1001", 10);
        ConversionRequest invalid = new ConversionRequest(2, "1002", 10);
        ConversionRequest outOfRange = new ConversionRequest(2, "1001", 37);

        HttpEntity<ConversionRequest> requestBody = new HttpEntity<>(valid);
        HttpEntity<ConversionRequest> requestBody1 = new HttpEntity<>(invalid);
        HttpEntity<ConversionRequest> requestBody2 = new HttpEntity<>(outOfRange);

        ResponseEntity<?> response = this.restTemplate.postForEntity("http://localhost:" + port + "/converter", requestBody, String.class);
        ResponseEntity<?> response1 = this.restTemplate.postForEntity("http://localhost:" + port + "/converter", requestBody1, String.class);
        ResponseEntity<?> response2 = this.restTemplate.postForEntity("http://localhost:" + port + "/converter", requestBody2, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response1.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertThat(response2.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
}