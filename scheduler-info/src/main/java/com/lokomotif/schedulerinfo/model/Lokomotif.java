package com.lokomotif.schedulerinfo.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "info-lokomotif")
public class Lokomotif {

    @Id
    private String kode;

    private String nama;

    private String dimensi;

    private String status;

    private String waktu;
}
