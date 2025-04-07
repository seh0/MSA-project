package com.example.demo.performanceservice.repository;

import com.example.demo.performanceservice.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    @Modifying
    @Query("DELETE FROM Performance p WHERE p.pEndTime < :thresholdTime")
    void deleteOldPerformances(LocalDateTime thresholdTime);
}
