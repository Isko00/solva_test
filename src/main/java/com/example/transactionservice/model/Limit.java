package com.example.transactionservice.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "limit")  // Optional: Specify table name if it's different
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal limitSum;

    private String limitCurrencyShortName;

    private LocalDateTime limitDatetime;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLimitSum() {
        return limitSum;
    }

    public void setLimitSum(BigDecimal limitSum) {
        this.limitSum = limitSum;
    }

    public String getLimitCurrencyShortName() {
        return limitCurrencyShortName;
    }

    public void setLimitCurrencyShortName(String limitCurrencyShortName) {
        this.limitCurrencyShortName = limitCurrencyShortName;
    }

    public LocalDateTime getLimitDatetime() {
        return limitDatetime;
    }

    public void setLimitDatetime(LocalDateTime limitDatetime) {
        this.limitDatetime = limitDatetime;
    }
}
