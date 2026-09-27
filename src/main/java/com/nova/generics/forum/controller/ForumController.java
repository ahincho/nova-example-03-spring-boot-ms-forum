package com.nova.generics.forum.controller;

import org.springframework.web.bind.annotation.*;
import pe.edu.nova.java.libs.observability.annotation.Traced;
import pe.edu.nova.java.libs.observability.annotation.Metered;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    @GetMapping("/topics")
    public List<Map<String, Object>> listTopics() {
        simulateLatency(20, 80);
        return List.of(
            Map.of("id", 1, "title", "¿Cómo usar @Traced?", "author", "Ana", "replies", 5),
            Map.of("id", 2, "title", "Error con Spring Boot 4", "author", "Pedro", "replies", 12),
            Map.of("id", 3, "title", "Mejores prácticas de observabilidad", "author", "Carlos", "replies", 8)
        );
    }

    @GetMapping("/topics/{id}")
    @Traced("forum.topic.detail")
    public Map<String, Object> getTopic(@PathVariable int id) {
        simulateLatency(30, 120);
        if (id == 999) {
            throw new RuntimeException("Error simulado en el foro");
        }
        return Map.of("id", id, "title", "¿Cómo usar @Traced?", "author", "Ana",
                "content", "Tengo una duda sobre la anotación @Traced...", "replies", 5);
    }

    @PostMapping("/topics/{topicId}/replies")
    @Metered("forum.reply.create")
    @Traced("forum.reply.create")
    public Map<String, Object> createReply(@PathVariable int topicId, @RequestBody Map<String, String> body) {
        simulateLatency(100, 300);
        return Map.of("topicId", topicId, "replyId", ThreadLocalRandom.current().nextInt(1000),
                "author", body.getOrDefault("author", "Anónimo"), "status", "published");
    }

    @GetMapping("/stats")
    @Metered
    public Map<String, Object> getStats() {
        simulateLatency(50, 200);
        return Map.of("totalTopics", 156, "totalReplies", 2340,
                "activeUsers", 45, "todayPosts", 23);
    }

    private void simulateLatency(int minMs, int maxMs) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(minMs, maxMs));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
