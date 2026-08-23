package com.projet.gestionEtudiant.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action; // CREATION, MODIFICATION, SUPPRESSION

    private String entityName; // Employe

    private Long entityId;

    private String username; // المستخدم الذي قام بالعملية

    @Column(length = 1000)
    private String details; // تفاصيل العملية

    private LocalDateTime timestamp;

    public AuditLog() {}

    public AuditLog(String action, String entityName, Long entityId, String username, String details) {
        this.action = action;
        this.entityName = entityName;
        this.entityId = entityId;
        this.username = username;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getEntityName() { return entityName; }
    public void setEntityName(String entityName) { this.entityName = entityName; }

    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}