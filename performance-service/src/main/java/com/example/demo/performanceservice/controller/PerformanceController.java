package com.example.demo.performanceservice.controller;

import com.example.demo.performanceservice.dto.PerformanceDTO;
import com.example.demo.performanceservice.entity.Performance;
import com.example.demo.performanceservice.service.PerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/performances")
public class PerformanceController {

    private final PerformanceService performanceService;
    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @GetMapping
    @Operation(summary = "공연 목록 조회", description = "등록된 모든 공연을 가져옵니다.")
    public ResponseEntity<List<Performance>> getPerformance() {
        return ResponseEntity.ok(performanceService.getPerformance());
    }

    @GetMapping("/{id}")
    @Operation(summary = "공연 ID로 공연 검색", description = "ID에 해당하는 공연 정보를 가져옵니다.")
    public ResponseEntity<PerformanceDTO> getPerformance(@PathVariable long id) {
        Performance performance = performanceService.getPerformanceById(id);
        if (performance == null) {
            return ResponseEntity.notFound().build();
        }
        PerformanceDTO dto = new PerformanceDTO(performance);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "공연 등록", description = "넘겨받은 객체로 공연을 등록합니다.")
    public ResponseEntity<?> savePerformance(@RequestBody Performance performance) {
        Performance savedPerformance = performanceService.savePerformance(performance);
        if(false) {
            return ResponseEntity.badRequest().body("관리자 권한이 없습니다.");
        }
        return ResponseEntity.status(201).body(savedPerformance);
    }

    @PutMapping("/{id}")
    @Operation(summary = "공연 수정", description = "ID에 해당하는 공연을 수정합니다.")
    public ResponseEntity<?> updatePerformance(@PathVariable long id, @RequestBody Performance performance) {
        if(false) {
            return ResponseEntity.badRequest().body("관리자 권한이 없습니다.");
        }
        return ResponseEntity.ok(performanceService.updatePerformance(id, performance));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "공연 삭제", description = "ID에 해당하는 공연을 삭제합니다.")
    public ResponseEntity<?> deletePerformance(@PathVariable long id) {
        if(false) {
            return ResponseEntity.badRequest().body("관리자 권한이 없습니다.");
        }
        return performanceService.deletePerformance(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
