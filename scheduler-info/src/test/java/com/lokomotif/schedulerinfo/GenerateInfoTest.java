package com.lokomotif.schedulerinfo;

import com.lokomotif.schedulerinfo.model.Lokomotif;
import com.lokomotif.schedulerinfo.service.ApiClientService;
import com.lokomotif.schedulerinfo.service.GenerateInfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class GenerateInfoTest {

    @Mock
    private ApiClientService apiClientService;

    @InjectMocks
    private GenerateInfo generateInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateAndLogInfo() {
        // Call the method to be tested
        generateInfo.generateAndLogInfo();

        // Capture the Lokomotif object passed to callAPI method
        ArgumentCaptor<Lokomotif> lokomotifCaptor = ArgumentCaptor.forClass(Lokomotif.class);
        verify(apiClientService).callAPI(lokomotifCaptor.capture());

        Lokomotif capturedLokomotif = lokomotifCaptor.getValue();

        // Verify the properties of the captured Lokomotif object
        assertNotNull(capturedLokomotif.getKode());
        assertTrue(capturedLokomotif.getKode().length() == 12);
        assertNotNull(capturedLokomotif.getNama());
        assertTrue(capturedLokomotif.getNama().startsWith("lokomotif"));
        assertNotNull(capturedLokomotif.getDimensi());
        assertTrue(capturedLokomotif.getDimensi().matches("10 x (10|5|20)"));
        assertNotNull(capturedLokomotif.getStatus());
        assertTrue(capturedLokomotif.getStatus().matches("Poor|Good|Excelent"));
        assertNotNull(capturedLokomotif.getWaktu());
    }
}
