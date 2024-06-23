package com.lokomotif.schedulerinfo.service;

import com.lokomotif.schedulerinfo.dto.SummaryDto;
import com.lokomotif.schedulerinfo.model.Summary;
import com.lokomotif.schedulerinfo.repository.SummaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class SummaryService {


    @Autowired
    private final SummaryRepository summaryRepository;

    private final RestTemplate restTemplate;
    private final MongoTemplate mongoTemplate;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.chat-id}")
    private String chatId;



    public SummaryService(SummaryRepository summaryRepository, RestTemplate restTemplate, MongoTemplate mongoTemplate) {
        this.summaryRepository = summaryRepository;
        this.restTemplate = restTemplate;
        this.mongoTemplate = mongoTemplate;
    }


    public List<SummaryDto> getDailyCounts() {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("tanggal", "status")
                        .count().as("jumlah"),
                Aggregation.project("jumlah")
                        .and("_id.tanggal").as("tanggal")
                        .and("_id.status").as("status")
        );

        AggregationResults<SummaryDto> results = mongoTemplate.aggregate(aggregation, "info-lokomotifs", SummaryDto.class);

        return results.getMappedResults();
    }

    public void saveSummary(){
        try{
            List<SummaryDto> summaries = getDailyCounts();
            Date now = new Date();
            for (SummaryDto summary: summaries) {
                Summary sum = new Summary();
                sum.setTime(String.valueOf(now));
                sum.setStatus(summary.getStatus());
                sum.setJumlah(summary.getJumlah());

                //save to postgresql
                summaryRepository.save(sum);

                //send to telegram
                String apiUrl = "https://api.telegram.org/bot" + botToken + "/sendMessage?chat_id=" + chatId + "&text=" + "Lets Chek Summary with status "+ summary.getStatus() +" in Mongo DB today on " + now;
                ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, null, String.class);
            }
        }catch(Exception e){
            log.error("data tidak tersimpan", e);
            throw new RuntimeException(e.getMessage());
        }

    }

    public List<Summary> getAllSummary() throws Exception {
        try {
            return summaryRepository.findAll();
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

}