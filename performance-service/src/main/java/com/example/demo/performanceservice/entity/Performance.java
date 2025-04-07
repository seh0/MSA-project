package com.example.demo.performanceservice.entity;

import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "performance")
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pId;

    @Column(nullable = false, length = 255)
    private String pTitle;

    @Column(length = 100)
    private String pManager;

    @Column(length = 100)
    private String pGenre;

    @Column(nullable = false)
    private LocalDateTime pDate;

    private Integer pSpot;
    private Integer pAllSpot;
    private Integer pPrice;

    @Column(length = 255)
    private String pPlace;

    @Column(length = 255)
    private String pImg;

    private LocalDateTime pEndTime;

    // 기본 생성자
    public Performance() {}

    // Getter 및 Setter
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

    public Optional<ResponseEntity<Object>> map(Object o) {
        return null;
    }
}
