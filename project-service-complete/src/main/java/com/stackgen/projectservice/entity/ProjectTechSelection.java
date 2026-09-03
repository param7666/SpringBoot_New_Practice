package com.stackgen.projectservice.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "project_tech_selection")
public class ProjectTechSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false, unique = true)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "generation_type", nullable = false, length = 20)
    private GenerationType generationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frontend_stack_id")
    private TechStack frontendStack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "backend_stack_id")
    private TechStack backendStack;

    public UUID getId() { return id; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public GenerationType getGenerationType() { return generationType; }
    public void setGenerationType(GenerationType generationType) { this.generationType = generationType; }
    public TechStack getFrontendStack() { return frontendStack; }
    public void setFrontendStack(TechStack frontendStack) { this.frontendStack = frontendStack; }
    public TechStack getBackendStack() { return backendStack; }
    public void setBackendStack(TechStack backendStack) { this.backendStack = backendStack; }
}
