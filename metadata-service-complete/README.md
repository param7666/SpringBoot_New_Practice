# StackGen Metadata Service

Spring Boot service that reads and validates the StackGen database dictionary, resolves FK relationships, persists normalized backend metadata, optionally parses frontend JSON, and exposes the resulting metadata to Project Service / Python generation.

## Main flow

Frontend/Project Service -> `POST /api/metadata/read` -> parsed rows

Frontend/Project Service -> `POST /api/metadata/validate` -> validation issues

Project Service -> `POST /api/metadata/extract` (multipart Excel + projectId + optional frontendJson) -> metadata persisted

Python/Project Service -> `GET /api/metadata/projects/{projectId}` -> entities + fields + relationships + generated CRUD endpoints

## Relationship logic

Rows marked `FK` and having `References` such as `entities.id` are resolved after all entities/fields are read. The service creates an `entity_relationships` row with source entity, target entity, FK field, join column and owning side. A FK with `UNIQUE` is treated as `ONE_TO_ONE`; otherwise it is `MANY_TO_ONE`.

This avoids trying to create the relationship while the target entity may not have been parsed yet.

## Excel columns expected

`Service | Table | Column | Data Type | Key | Constraints | References | Description`

Backend rows are selected when Service contains `Metadata Service - Backend`. Other service rows in the same workbook are ignored by backend extraction.

## Run

1. Start PostgreSQL: `docker compose up -d`
2. Start Eureka if you use service discovery.
3. Run: `mvn spring-boot:run`

Default port: `8082`

## Postman

### Read
POST `http://localhost:8082/api/metadata/read`
form-data: `file` = Excel

### Validate
POST `http://localhost:8082/api/metadata/validate`
form-data: `file` = Excel

### Extract
POST `http://localhost:8082/api/metadata/extract?projectId=<UUID>&userId=<UUID>`
form-data:
- `file` = Excel
- `frontendJson` = optional JSON string

### Get stored metadata
GET `http://localhost:8082/api/metadata/projects/<projectId>`

## Important integration note

Metadata Service does not directly query Project Service's database. Project Service sends the stored Excel to this service over HTTP. This keeps the services decoupled and makes the metadata service reusable.
