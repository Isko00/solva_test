package com.example.transactionservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Account() {
        this.name = "temp";
    }

    public Account(String name) {
        this.name = name;
    }
}
