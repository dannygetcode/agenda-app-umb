package com.umb.agenda.agenda_backend.service;

import com.umb.agenda.agenda_backend.dto.NoteDTO;
import com.umb.agenda.agenda_backend.dto.NoteMapper;
import com.umb.agenda.agenda_backend.model.Note;
import com.umb.agenda.agenda_backend.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class NoteService {
  private final NoteRepository repo;
  public NoteService(NoteRepository repo) { this.repo = repo; }

  public List<NoteDTO> list(Instant since){
    var data = (since == null)
        ? repo.findAllByDeletedAtIsNullOrderByPinnedDescUpdatedAtDesc()
        : repo.findByUpdatedAtGreaterThanEqualAndDeletedAtIsNullOrderByPinnedDescUpdatedAtDesc(since);
    return data.stream().map(NoteMapper::toDto).toList();
  }

  public NoteDTO get(String id){
    return repo.findById(id).map(NoteMapper::toDto).orElseThrow();
  }

  public NoteDTO upsert(String id, NoteDTO dto){
    var note = repo.findById(id).orElseGet(() -> Note.builder().id(id).build());
    note.setTitle(dto.title());
    note.setContent(dto.content());
    note.setColor(dto.color());
    note.setPinned(dto.pinned());
    note.setArchived(dto.archived());
    note.setReminderAt(dto.reminderAt());
    var saved = repo.save(note);
    return NoteMapper.toDto(saved);
  }

  public void softDelete(String id){
    var note = repo.findById(id).orElseThrow();
    note.setDeletedAt(Instant.now());
    repo.save(note);
  }
}
