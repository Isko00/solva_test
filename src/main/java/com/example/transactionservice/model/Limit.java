package com.example.transactionservice.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal limitSum;
    private String limitCurrencyShortName;
    private LocalDateTime limitDatetime;

    // Getters and setters
}
