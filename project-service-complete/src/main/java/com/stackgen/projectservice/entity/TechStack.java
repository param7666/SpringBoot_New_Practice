package com.stackgen.projectservice.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tech_stacks")
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category;

    @Column(nullable = false, length = 50)
    private String language;

    @Column(length = 30)
    private String version;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    public enum Category { FRONTEND, BACKEND }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
