package com.stackgen.metadataservice.entity;
import jakarta.persistence.*; import java.util.UUID;
@Entity @Table(name="blueprints") public class Blueprint { @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id; @Column(name="project_id",nullable=false) private UUID projectId; @Column(columnDefinition="text") private String blueprintJson; public UUID getId(){return id;} public UUID getProjectId(){return projectId;} public void setProjectId(UUID v){projectId=v;} public String getBlueprintJson(){return blueprintJson;} public void setBlueprintJson(String v){blueprintJson=v;} }
