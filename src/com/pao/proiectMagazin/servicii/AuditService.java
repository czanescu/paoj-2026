package com.pao.proiectMagazin.servicii;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public final class AuditService {
    private static volatile AuditService instance;
    private final Path auditPath;

    private AuditService() {
        this.auditPath = Paths.get("audit.csv");
    }

    public static AuditService getInstance() {
        if (instance == null) {
            synchronized (AuditService.class) {
                if (instance == null) {
                    instance = new AuditService();
                }
            }
        }
        return instance;
    }

    public synchronized void logAction(String actionName) {
        if (actionName == null || actionName.isBlank()) {
            throw new IllegalArgumentException("actionName nu poate fi null sau gol");
        }
        String line = actionName + "," + LocalDateTime.now();
        try (BufferedWriter writer = Files.newBufferedWriter(
                auditPath,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException("Nu s-a reușit scrierea log-ului", e);
        }
    }
}
