package com.umb.agenda.agenda_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "notes")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Note {
  @Id
  private String id;

  @Column(nullable = false) private String title;
  @Column(columnDefinition = "text", nullable = false) private String content;

  private String color;
  private boolean pinned;
  private boolean archived;
  private Instant reminderAt;

  @Column(nullable = false) private Instant createdAt;
  @Column(nullable = false) private Instant updatedAt;
  private Instant deletedAt;

  @PrePersist
  public void prePersist() {
    if (id == null) id = UUID.randomUUID().toString();
    var now = Instant.now();
    createdAt = now;
    updatedAt = now;
  }

  @PreUpdate
  public void preUpdate() {
    updatedAt = Instant.now();
  }
}

