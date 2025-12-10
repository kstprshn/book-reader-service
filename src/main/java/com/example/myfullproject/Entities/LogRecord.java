package com.example.myfullproject.Entities;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "log_record")
@Data
public class LogRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(name = "action", length = 255, nullable = false)
    private String action;

    @Column(name = "details", length = 2048)
    private String details;

    @PrePersist
    public void init() {
        this.timestamp = new Date();
    }
}