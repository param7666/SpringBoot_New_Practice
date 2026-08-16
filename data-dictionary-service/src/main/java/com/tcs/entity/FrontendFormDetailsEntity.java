package com.tcs.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

/**
 * Stores the user-filled form describing frontend behavior: pages,
 * components (buttons/forms/tables), and the events that connect them to
 * backend APIs. One row per generation request. Each *Json field holds a
 * JSON array serialized to text (kept simple — no extra JSONB converter
 * dependency required).
 */
@Entity
@Table(name = "frontend_form_details")
@Data
public class FrontendFormDetailsEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID generationRequestId;

   // @Lob
    @Column(columnDefinition = "TEXT")
    private String pagesJson;       // e.g. [{"name":"LoginPage","route":"/login"}]

   // @Lob
    @Column(columnDefinition = "TEXT")
    private String componentsJson;  // e.g. [{"name":"LoginForm","type":"FORM","fields":["email","password"]}]

   // @Lob
    @Column(columnDefinition = "TEXT")
    private String eventsJson;      // e.g. [{"trigger":"onSubmit","component":"LoginForm","action":"calls Login API"}]

   // @Lob
    @Column(columnDefinition = "TEXT")
    private String extraNotes;      // free-text anything else the user wants to mention

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
