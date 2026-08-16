package com.tcs.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

import com.tcs.enums.GenerationStatus;

@Entity
@Table(name = "generation_requests")
@Data
public class GenerationRequestEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String projectId;

    @Column(nullable = false)
    private String techStack;

    // Raw multi-sheet text dump from DataDictionaryParserService.
    // Stored here (not just in memory) so the background job can read it
    // later, independent of the original HTTP request.
   // @Lob
    @Column(columnDefinition = "TEXT")
    private String dictionaryText;

    // Path to the wireframe file on disk, if one was uploaded. Null if none.
    private String wireframePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenerationStatus status;

    // Raw JSON string returned by Django once generation succeeds.
    //@Lob
    @Column(columnDefinition = "TEXT")
    private String resultJson;

   // @Lob
    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = GenerationStatus.QUEUED;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
