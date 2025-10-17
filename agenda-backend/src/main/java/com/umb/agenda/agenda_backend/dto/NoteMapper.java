package com.umb.agenda.agenda_backend.dto;
import com.umb.agenda.agenda_backend.model.Note;


public class NoteMapper {
  public static NoteDTO toDto(Note n){
    return new NoteDTO(n.getId(), n.getTitle(), n.getContent(), n.getColor(),
      n.isPinned(), n.isArchived(), n.getReminderAt(),
      n.getCreatedAt(), n.getUpdatedAt(), n.getDeletedAt());
  }
  public static Note toEntity(NoteDTO d){
    return Note.builder()
      .id(d.id()).title(d.title()).content(d.content()).color(d.color())
      .pinned(d.pinned()).archived(d.archived()).reminderAt(d.reminderAt())
      .createdAt(d.createdAt()).updatedAt(d.updatedAt()).deletedAt(d.deletedAt())
      .build();
  }
}
