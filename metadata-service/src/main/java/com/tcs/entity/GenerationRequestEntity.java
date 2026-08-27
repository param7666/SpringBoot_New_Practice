package com.tcs.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;
import com.tcs.enums.GenerationStatus;

/**
 * One row per generation request. Owned by a user in the separate
 * User/Auth service — since this is a different microservice with its
 * own database, we store just the userId (from the JWT / gateway header),
 * NOT a JPA relation to a User entity. There is no shared database to join
 * across services.
 */
@Entity
@Table(name = "generation_requests1")
@Data
public class GenerationRequestEntity {

    @Id
    @GeneratedValue
    private UUID id;

    // Owning user's id, taken from the JWT (X-Auth-UserId header, forwarded
    // by the gateway). Used for ownership checks — a user can only see
    // their own generation jobs unless they're an admin.
    @Column(nullable = false)
    private Long userId;

    private String projectId;

    @Column(nullable = false)
    private String techStack;

    // Raw multi-sheet text dump from DataDictionaryParserService — sent to Django as-is.
    @Column(columnDefinition = "TEXT")
    private String dictionaryText;

    // Path to the wireframe file on disk, if one was uploaded. Null if none.
    private String wireframePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenerationStatus status;

    // Full raw JSON response returned by Django once generation succeeds.
    @Column(columnDefinition = "TEXT")
    private String resultJson;

    // Just the "updatedDictionary" portion of Django's response, extracted
    // for downstream steps that only need the refined dictionary.
    @Column(columnDefinition = "TEXT")
    private String updatedDictionaryText;

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