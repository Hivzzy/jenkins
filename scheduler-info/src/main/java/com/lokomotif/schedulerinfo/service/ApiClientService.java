package com.lokomotif.schedulerinfo.service;

import com.lokomotif.schedulerinfo.model.Lokomotif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;


@Service
public class ApiClientService {

    @Autowired
    private final RestTemplate restTemplate;

    public ApiClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void callAPI(Lokomotif infoLoko) {
        String apiUrl = "http://localhost:8081/api/send"; // Ganti dengan URL API sesuai kebutuhan

        // Membuat header untuk permintaan POST
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Sesuaikan dengan jenis media yang Anda kirim

        // Membuat entitas HTTP dengan data InfoLokomotif yang akan dikirimkan
        HttpEntity<Lokomotif> requestEntity = new HttpEntity<>(infoLoko, headers);

        // Melakukan permintaan POST ke API
        String response = restTemplate.postForObject(apiUrl, requestEntity, String.class);
    }

}
