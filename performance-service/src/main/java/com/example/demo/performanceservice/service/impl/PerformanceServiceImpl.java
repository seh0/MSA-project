package com.example.demo.performanceservice.service.impl;

import com.example.demo.performanceservice.entity.Performance;
import com.example.demo.performanceservice.repository.PerformanceRepository;
import com.example.demo.performanceservice.service.PerformanceService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;

    public PerformanceServiceImpl(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    @Override
    public List<Performance> getPerformance() {
        return performanceRepository.findAll();
    }

    @Override
    public Performance savePerformance(Performance performance) {
        return performanceRepository.save(performance);
    }

    @Override
    public Performance getPerformanceById(long id) {
        return performanceRepository.getById(id);
    }

    @Override
    public Performance updatePerformance(long id, Performance performance) {
        Performance performanceToUpdate = performanceRepository.getReferenceById(id);
        return performanceRepository.save(performanceToUpdate);
    }

    @Override
    public boolean deletePerformance(long id) {
        if(performanceRepository.getReferenceById(id) == null){
            return false;
        }
        performanceRepository.deleteById(id);
        return true;
    }
    @Override
    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void cleanPerformance() {
        LocalDateTime thresholdTime = LocalDateTime.now().plusMonths(2);
        performanceRepository.deleteOldPerformances(thresholdTime);
    }


}
