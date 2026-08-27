package com.tcs.entity;

import jakarta.persistence.*;
import lombok.Data;
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

    @Column(nullable = false)
    private Long userId;

    private String projectId;

    @Column(nullable = false)
    private String techStack;

    @Column(columnDefinition = "TEXT")
    private String dictionaryText;

    private String wireframePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenerationStatus status;

    @Column(columnDefinition = "TEXT")
    private String resultJson;

    @Column(columnDefinition = "TEXT")
    private String updatedDictionaryText;

    // Id from the Project Generator service, once the project has been
    // built and its zip stored there. Used to re-download without
    // rebuilding the project every time.
    private UUID projectGeneratorId;

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