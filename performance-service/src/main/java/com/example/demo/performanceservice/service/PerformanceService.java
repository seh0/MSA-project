package com.example.demo.performanceservice.service;

import com.example.demo.performanceservice.entity.Performance;

import java.util.List;

public interface PerformanceService {
    public List<Performance> getPerformance();
    public Performance savePerformance(Performance performance);
    public Performance getPerformanceById(long id);
    public Performance updatePerformance(long id, Performance performance);
    public boolean deletePerformance(long id);
    public void cleanPerformance();
}
