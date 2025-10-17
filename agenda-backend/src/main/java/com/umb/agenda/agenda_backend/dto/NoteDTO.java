package com.umb.agenda.agenda_backend.dto;

import java.time.Instant;

public record NoteDTO(
  String id, String title, String content, String color,
  boolean pinned, boolean archived, Instant reminderAt,
  Instant createdAt, Instant updatedAt, Instant deletedAt
) {}
