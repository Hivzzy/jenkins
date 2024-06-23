package com.lokomotif.schedulerinfo.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class SummaryDto {
    @Id
    private String id;
    private String time;
    private String status;
    private int jumlah;

}
