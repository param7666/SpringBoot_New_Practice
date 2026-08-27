package com.tcs.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

/**
 * Stores the user-filled frontend form (pages, components, events).
 * Ownership is derived through generationRequestId -> GenerationRequestEntity.userId,
 * so no separate userId column is needed here — one row always belongs to
 * exactly one generation request, which already carries the owning user.
 */
@Entity
@Table(name = "frontend_form_details1")
@Data
public class FrontendFormDetailsEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID generationRequestId;

    @Column(columnDefinition = "TEXT")
    private String pagesJson;

    @Column(columnDefinition = "TEXT")
    private String componentsJson;

    @Column(columnDefinition = "TEXT")
    private String eventsJson;

    @Column(columnDefinition = "TEXT")
    private String extraNotes;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
