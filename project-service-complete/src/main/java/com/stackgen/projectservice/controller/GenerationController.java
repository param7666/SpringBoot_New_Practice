package com.stackgen.projectservice.controller;

import com.stackgen.projectservice.dto.GenerationStatusResponse;
import com.stackgen.projectservice.service.GenerationStatusService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/projects")
public class GenerationController {

    private final GenerationStatusService statusService;

    public GenerationController(GenerationStatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/{projectId}/generation-status")
    public GenerationStatusResponse getStatus(
            @RequestParam UUID userId,
            @PathVariable UUID projectId) {
        return statusService.getStatus(userId, projectId);
    }

    @GetMapping(value = "/{projectId}/generation-stream", produces = "text/event-stream")
    public SseEmitter stream(
            @RequestParam UUID userId,
            @PathVariable UUID projectId) {

        SseEmitter emitter = new SseEmitter(0L);

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                while (true) {
                    GenerationStatusResponse status = statusService.getStatus(userId, projectId);
                    emitter.send(SseEmitter.event()
                            .name("generation-status")
                            .data(status));

                    if (status.status().name().equals("COMPLETED") ||
                            status.status().name().equals("FAILED")) {
                        emitter.complete();
                        break;
                    }

                    Thread.sleep(2000);
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });

        return emitter;
    }
}
