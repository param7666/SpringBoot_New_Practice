# Project Service

Spring Boot 4.1 / Java 21 project orchestration service.

## Main flow

1. Create project with name, description, generation type and tech stacks.
2. Upload backend data dictionary when BACKEND/FULLSTACK.
3. Send frontend JSON when FRONTEND/FULLSTACK.
4. Store all input first.
5. Create a generation record and pipeline steps.
6. Start async generation.
7. Call Metadata Service to extract the Excel data dictionary.
8. Call Python service with extracted metadata + frontend JSON + user/project/stack information.
9. Python generates and stores ZIP.
10. Project Service saves `zip_file_key`, marks project GENERATED and generation COMPLETED.
11. Angular can poll the status endpoint or use SSE.
12. Angular downloads the ZIP through the download endpoint.

## API

### Create project

POST `/api/projects`

Content-Type: multipart/form-data

Part `project`:
```json
{
  "userId": "11111111-1111-1111-1111-111111111111",
  "name": "Employee Management",
  "description": "Employee management project",
  "generationType": "FULLSTACK",
  "frontendStackId": "22222222-2222-2222-2222-222222222222",
  "backendStackId": "33333333-3333-3333-3333-333333333333"
}
```

Parts:
- `dataDictionary`: `.xlsx`
- `frontendJson`: raw JSON string

### List projects

GET `/api/projects?userId={userId}`

### Project details

GET `/api/projects/{projectId}?userId={userId}`

### Generation status

GET `/api/projects/{projectId}/generation-status?userId={userId}`

### Live generation stream

GET `/api/projects/{projectId}/generation-stream?userId={userId}`

### Download

GET `/api/projects/{projectId}/download?userId={userId}`

## Important integration contract

Metadata Service:
`POST ${services.metadata.base-url}${services.metadata.extract-path}`

Request is `multipart/form-data`:
- `projectId`
- `userId`
- `file` = original data-dictionary Excel

Expected response: JSON containing the extracted data dictionary.

If your existing Metadata Service uses a different endpoint or part name, change:
`services.metadata.extract-path` and the multipart field in `GenerationWorkerService`.

Python Service:
`POST ${services.python.base-url}${services.python.generate-path}`

Request:
```json
{
  "projectId": "uuid",
  "userId": "uuid",
  "generationType": "FULLSTACK",
  "frontend": {
    "name": "Angular",
    "language": "TypeScript",
    "version": "20"
  },
  "backend": {
    "name": "Spring Boot",
    "language": "Java",
    "version": "21"
  },
  "dataDictionary": {},
  "frontendJson": {}
}
```

Expected response:
```json
{
  "status": "COMPLETED",
  "zipFileKey": "/shared/generated/employee-management.zip",
  "zipFileName": "employee-management.zip"
}
```

### Shared ZIP storage

The ZIP path/key returned by Python must be accessible to Project Service.

For local development, a shared filesystem path works.

For Docker/Kubernetes/production, prefer object storage such as S3-compatible storage:
- Python uploads the ZIP.
- Python returns the object key.
- Project Service stores the object key in `projects.zip_file_key`.
- Download API reads the object from storage.

Do not return a private Python-container filesystem path when the services run in separate containers.

## Security

The sample API accepts `userId` as a request parameter/body so it can be tested easily.

In your real application, do **not** trust a client-provided user ID. Read the authenticated user ID from your JWT/SecurityContext/Gateway identity and use that value for ownership checks.

## Production improvements

- Replace `ddl-auto=update` with Flyway/Liquibase.
- Add retry/timeouts/circuit breaker for Metadata/Python calls.
- Use a durable queue (RabbitMQ/Kafka/SQS) if generation must survive service restarts.
- Use object storage for ZIPs.
- Persist idempotency/generation attempt information.
- Add authentication/authorization.
- For high traffic, use a shared SSE/WebSocket infrastructure instead of one polling thread per SSE connection.
