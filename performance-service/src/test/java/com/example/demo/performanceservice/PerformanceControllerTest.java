package com.example.demo.performanceservice;

import com.example.demo.performanceservice.controller.PerformanceController;
import com.example.demo.performanceservice.entity.Performance;
import com.example.demo.performanceservice.repository.PerformanceRepository;
import com.example.demo.performanceservice.service.PerformanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PerformanceController.class)
public class PerformanceControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private PerformanceRepository performanceRepository;
    @SpyBean
    private PerformanceService performanceCleanService;
    @MockBean
    private PerformanceService performanceService;

    @Autowired
    private ObjectMapper objectMapper;

    private Performance testPerformance;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        testPerformance = new Performance();
        testPerformance.setPId(1L);
        testPerformance.setPAllSpot(100);
        testPerformance.setPSpot(50);
        testPerformance.setPDate(LocalDateTime.now());
        testPerformance.setPEndTime(LocalDateTime.now().plusHours(2));
        testPerformance.setPGenre("연극");
        testPerformance.setPImg("test.png");
        testPerformance.setPPlace("서울시");
        testPerformance.setPTitle("HELLO WORLD");
        testPerformance.setPManager("홍길동");
        testPerformance.setPPrice(170000);
    }
    @Test
    public void testCleanPerformance_ScheduledExecution() throws Exception {
        // Given
        LocalDateTime now = LocalDateTime.now();
        ArgumentCaptor<LocalDateTime> captor = ArgumentCaptor.forClass(LocalDateTime.class);

        // When: cleanPerformance()를 직접 호출하여 검증
        performanceCleanService.cleanPerformance();

        // Then: deleteOldPerformances가 정확한 인자로 호출되었는지 검증
        verify(performanceRepository, times(1)).deleteOldPerformances(captor.capture());

        // 삭제 요청한 시간이 현재 시간과 유사한지 확인
        LocalDateTime capturedTime = captor.getValue();
        assert capturedTime.isBefore(now.plusSeconds(1)) && capturedTime.isAfter(now.minusSeconds(1));
    }

    @Test
    public void testPerformanceDTO_ContainsPerformanceTime() throws Exception {
        // Given: Entity에서 데이터를 받아오는 부분 (PerformanceService에서 수행)
        Performance performance = new Performance();
        performance.setPId(1L);
        performance.setPTitle("테스트 공연");
        performance.setPDate(LocalDateTime.of(2025, 4, 3, 18, 0));
        performance.setPEndTime(LocalDateTime.of(2025, 4, 3, 20, 0));

        // 서비스에서 데이터 반환을 Mock으로 설정
        when(performanceService.getPerformanceById(1L)).thenReturn(performance);

        // When & Then: API 호출 및 응답 검증
        mockMvc.perform(get("/api/performances/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pid").value(1L))
                .andExpect(jsonPath("$.performanceTime").value("PT2H")); // 2시간(ISO-8601 Duration 형식)
    }

    @Test
    void getPerformance() throws Exception {
        List<Performance> performance = Arrays.asList(testPerformance);
        when(performanceService.getPerformance()).thenReturn(performance);
        mockMvc.perform(get("/api/performances"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pid").value(1))
                .andExpect(jsonPath("$[0].ptitle").value("HELLO WORLD"))
                .andExpect(jsonPath("$[0].pmanager").value("홍길동"))
                .andExpect(jsonPath("$[0].pgenre").value("연극"))
                .andExpect(jsonPath("$[0].pspot").value(50))
                .andExpect(jsonPath("$[0].pallSpot").value(100))
                .andExpect(jsonPath("$[0].pprice").value(170000))
                .andExpect(jsonPath("$[0].pplace").value("서울시"))
                .andExpect(jsonPath("$[0].pimg").value("test.png"));
    }

    @Test
    void getPerformanceById() throws Exception {
        when(performanceService.getPerformanceById(1L)).thenReturn(testPerformance);

        mockMvc.perform(get("/api/performances/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pid").value(1))
                .andExpect(jsonPath("$.ptitle").value("HELLO WORLD"))
                .andExpect(jsonPath("$.pmanager").value("홍길동"))
                .andExpect(jsonPath("$.pgenre").value("연극"))
                .andExpect(jsonPath("$.pspot").value(50))
                .andExpect(jsonPath("$.pallSpot").value(100))
                .andExpect(jsonPath("$.pprice").value(170000))
                .andExpect(jsonPath("$.pplace").value("서울시"))
                .andExpect(jsonPath("$.pimg").value("test.png"));
    }

    @Test
    void postPerformance() throws Exception {
        when(performanceService.savePerformance(any(Performance.class))).thenReturn(testPerformance);

        mockMvc.perform(post("/api/performances")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPerformance)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pid").value(1))
                .andExpect(jsonPath("$.ptitle").value("HELLO WORLD"))
                .andExpect(jsonPath("$.pmanager").value("홍길동"))
                .andExpect(jsonPath("$.pgenre").value("연극"))
                .andExpect(jsonPath("$.pspot").value(50))
                .andExpect(jsonPath("$.pallSpot").value(100))
                .andExpect(jsonPath("$.pprice").value(170000))
                .andExpect(jsonPath("$.pplace").value("서울시"))
                .andExpect(jsonPath("$.pimg").value("test.png"));
    }

    @Test
    void putPerformance() throws Exception {
        when(performanceService.updatePerformance(eq(1L), any(Performance.class))).thenReturn(testPerformance);
        mockMvc.perform(put("/api/performances/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPerformance)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pid").value(1))
                .andExpect(jsonPath("$.ptitle").value("HELLO WORLD"))
                .andExpect(jsonPath("$.pmanager").value("홍길동"))
                .andExpect(jsonPath("$.pgenre").value("연극"))
                .andExpect(jsonPath("$.pspot").value(50))
                .andExpect(jsonPath("$.pallSpot").value(100))
                .andExpect(jsonPath("$.pprice").value(170000))
                .andExpect(jsonPath("$.pplace").value("서울시"))
                .andExpect(jsonPath("$.pimg").value("test.png"));
    }

    @Test
    void deletePerformance() throws Exception {
        when(performanceService.deletePerformance(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/performances/1"))
                .andExpect(status().isOk());
    }
}
