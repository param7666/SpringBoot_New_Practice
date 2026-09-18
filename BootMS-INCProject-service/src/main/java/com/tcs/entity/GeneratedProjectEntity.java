package com.tcs.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "generated_projects")
@Data
public class GeneratedProjectEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String projectName;

    // Raw zip bytes. Maps to bytea in PostgreSQL automatically.
    @Lob
    @Column(nullable = false)
    private byte[] zipData;

    @Column(nullable = false)
    private String status; // COMPLETED / FAILED

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
