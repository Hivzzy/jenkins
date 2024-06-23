package com.lokomotif.schedulerinfo;

import com.lokomotif.schedulerinfo.dto.SummaryDto;
import com.lokomotif.schedulerinfo.model.Summary;
import com.lokomotif.schedulerinfo.repository.SummaryRepository;
import com.lokomotif.schedulerinfo.service.SummaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SummaryServiceTest {

    @Mock
    private SummaryRepository summaryRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private SummaryService summaryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Manually set the fields instead of relying on @Value
        summaryService = new SummaryService(summaryRepository, restTemplate, mongoTemplate);

        try {
            Field botTokenField = SummaryService.class.getDeclaredField("botToken");
            botTokenField.setAccessible(true);
            botTokenField.set(summaryService, "testToken");

            Field chatIdField = SummaryService.class.getDeclaredField("chatId");
            chatIdField.setAccessible(true);
            chatIdField.set(summaryService, "testChatId");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllSummary() throws Exception {
        // Prepare mock data
        List<Summary> mockSummaries = new ArrayList<>();
        mockSummaries.add(new Summary());
        mockSummaries.get(0).setStatus("Good");
        mockSummaries.get(0).setJumlah(10);
        mockSummaries.get(0).setTime("2023-06-01T10:00:00");

        // Mock the findAll method
        when(summaryRepository.findAll()).thenReturn(mockSummaries);

        // Call the method to be tested
        List<Summary> result = summaryService.getAllSummary();

        // Verify the results
        assertEquals(1, result.size());
        assertEquals("Good", result.get(0).getStatus());
        assertEquals(10, result.get(0).getJumlah());
        assertEquals("2023-06-01T10:00:00", result.get(0).getTime());
    }

}
