package com.example.demo.performanceservice.dto;

import com.example.demo.performanceservice.entity.Performance;

import java.time.Duration;
import java.time.LocalDateTime;

public class PerformanceDTO {

    private Long pId;
    private String pTitle;
    private String pManager;
    private String pGenre;
    private LocalDateTime pDate;
    private Integer pSpot;
    private Integer pAllSpot;
    private Integer pPrice;
    private String pPlace;
    private String pImg;
    private LocalDateTime pEndTime;
    private Duration performanceTime;

    // 기본 생성자
    public PerformanceDTO() {}

    // 모든 필드를 포함하는 생성자
    public PerformanceDTO(Performance performance) {
        this.pId = performance.getPId();
        this.pTitle = performance.getPTitle();
        this.pManager = performance.getPManager();
        this.pGenre = performance.getPGenre();
        this.pDate = performance.getPDate();
        this.pSpot = performance.getPSpot();
        this.pAllSpot = performance.getPAllSpot();
        this.pPrice = performance.getPPrice();
        this.pPlace = performance.getPPlace();
        this.pImg = performance.getPImg();
        this.pEndTime = performance.getPEndTime();
        this.performanceTime = Duration.between(performance.getPDate(), performance.getPEndTime());
    }

    // Getter 및 Setter 메서드
    public Long getPId() {
        return pId;
    }

    public void setPId(Long pId) {
        this.pId = pId;
    }

    public String getPTitle() {
        return pTitle;
    }

    public void setPTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getPManager() {
        return pManager;
    }

    public void setPManager(String pManager) {
        this.pManager = pManager;
    }

    public String getPGenre() {
        return pGenre;
    }

    public void setPGenre(String pGenre) {
        this.pGenre = pGenre;
    }

    public LocalDateTime getPDate() {
        return pDate;
    }

    public void setPDate(LocalDateTime pDate) {
        this.pDate = pDate;
    }

    public Integer getPSpot() {
        return pSpot;
    }

    public void setPSpot(Integer pSpot) {
        this.pSpot = pSpot;
    }

    public Integer getPAllSpot() {
        return pAllSpot;
    }

    public void setPAllSpot(Integer pAllSpot) {
        this.pAllSpot = pAllSpot;
    }

    public Integer getPPrice() {
        return pPrice;
    }

    public void setPPrice(Integer pPrice) {
        this.pPrice = pPrice;
    }

    public String getPPlace() {
        return pPlace;
    }

    public void setPPlace(String pPlace) {
        this.pPlace = pPlace;
    }

    public String getPImg() {
        return pImg;
    }

    public void setPImg(String pImg) {
        this.pImg = pImg;
    }

    public LocalDateTime getPEndTime() {
        return pEndTime;
    }

    public void setPEndTime(LocalDateTime pEndTime) {
        this.pEndTime = pEndTime;
    }

    public Duration getPerformanceTime() {
        return performanceTime;
    }

    public void setPerformanceTime(Duration performanceTime) {
        this.performanceTime = performanceTime;
    }
}
