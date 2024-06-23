package com.lokomotif.schedulerinfo.controller;

import com.lokomotif.schedulerinfo.dto.SummaryDto;
import com.lokomotif.schedulerinfo.model.Summary;
import com.lokomotif.schedulerinfo.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("summaries")
public class SummaryController {

    @Autowired
    private final SummaryService summaryService;


    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/getAll")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<List<SummaryDto>> getAll() throws Exception {
        List<Summary> summaries = summaryService.getAllSummary();
        List<SummaryDto> dtos = new ArrayList<>();  // Create a list to hold SummaryDto objects

        for (Summary summary : summaries) {
            dtos.add(SummaryDto.builder().id(String.valueOf(summary.getId())).time(summary.getTime()).status(summary.getStatus()).jumlah(summary.getJumlah()).build());  // Access id property of each Summary object
        }

        return ResponseEntity.ok(dtos);
    }
}
