package com.umb.agenda.agenda_backend.repository;

import com.umb.agenda.agenda_backend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, String> {

    
    List<Note> findAllByDeletedAtIsNullOrderByPinnedDescUpdatedAtDesc();    
    List<Note> findByUpdatedAtGreaterThanEqualAndDeletedAtIsNullOrderByPinnedDescUpdatedAtDesc(Instant since);
}
