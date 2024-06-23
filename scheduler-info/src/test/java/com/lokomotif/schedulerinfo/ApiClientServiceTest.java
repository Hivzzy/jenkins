package com.lokomotif.schedulerinfo;


import com.lokomotif.schedulerinfo.model.Lokomotif;
import com.lokomotif.schedulerinfo.service.ApiClientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApiClientServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ApiClientService apiClientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCallAPI() {
        Lokomotif infoLoko = new Lokomotif();
        // Initialize Lokomotif object with necessary data

        String apiUrl = "http://localhost:8081/api/send";
        String expectedResponse = "Success";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Lokomotif> requestEntity = new HttpEntity<>(infoLoko, headers);

        // Mock the RestTemplate's response
        when(restTemplate.postForObject(apiUrl, requestEntity, String.class)).thenReturn(expectedResponse);

        // Call the method to be tested
        apiClientService.callAPI(infoLoko);

        // Verify if the RestTemplate's postForObject method was called with the correct parameters
        verify(restTemplate).postForObject(apiUrl, requestEntity, String.class);
    }
}
