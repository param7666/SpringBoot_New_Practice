package com.stackgen.metadataservice.entity;
import jakarta.persistence.*; import java.util.UUID;
@Entity @Table(name="endpoint_path_variables")
public class EndpointPathVariable { @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id; @Column(name="endpoint_id",nullable=false) private UUID endpointId; @Column(nullable=false,length=50) private String name; @Column(name="data_type",nullable=false,length=50) private String dataType; public UUID getId(){return id;} public UUID getEndpointId(){return endpointId;} public void setEndpointId(UUID v){endpointId=v;} public String getName(){return name;} public void setName(String v){name=v;} public String getDataType(){return dataType;} public void setDataType(String v){dataType=v;}
}
