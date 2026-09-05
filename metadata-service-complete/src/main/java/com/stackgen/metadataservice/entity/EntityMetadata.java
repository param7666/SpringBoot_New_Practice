package com.stackgen.metadataservice.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="entities", indexes=@Index(name="idx_entities_project", columnList="project_id"))
public class EntityMetadata {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(name="project_id", nullable=false) private UUID projectId;
 @Column(nullable=false, length=100) private String name;
 @Column(name="table_name", nullable=false, length=100) private String tableName;
 @Column(name="created_at", nullable=false) private Instant createdAt=Instant.now();
 public UUID getId(){return id;} public UUID getProjectId(){return projectId;} public void setProjectId(UUID v){projectId=v;}
 public String getName(){return name;} public void setName(String v){name=v;} public String getTableName(){return tableName;} public void setTableName(String v){tableName=v;}
 public Instant getCreatedAt(){return createdAt;}
}
