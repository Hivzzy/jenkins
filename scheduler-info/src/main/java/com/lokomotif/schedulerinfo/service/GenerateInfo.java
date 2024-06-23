package com.lokomotif.schedulerinfo.service;

import com.lokomotif.schedulerinfo.model.Lokomotif;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class GenerateInfo {

    private static ApiClientService apiClientService;

    public GenerateInfo(ApiClientService apiClientService) {
        GenerateInfo.apiClientService = apiClientService;
    }

    public void generateAndLogInfo() {
        String[] status = {"Poor", "Good", "Excelent"};
        String[] dimension = {"10 x 10", "10 x 5", "10 x 20"};
        Random random = new Random();
        Lokomotif lokomotif = Lokomotif.builder().kode(UUID.randomUUID().toString().substring(0,12))
                .nama("lokomotif" + UUID.randomUUID().toString().substring(0,3))
                .dimensi(dimension[random.nextInt(dimension.length)])
                .status(status[random.nextInt(status.length)])
                .waktu(LocalDateTime.now().toString()).build();

        apiClientService.callAPI(lokomotif);
        log.info(String.format("Data => %s", lokomotif.toString()));
    }
}
