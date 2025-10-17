package com.umb.agenda.agenda_backend.controller;

import com.umb.agenda.agenda_backend.dto.NoteDTO;
import com.umb.agenda.agenda_backend.service.NoteService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
  private final NoteService service;
  public NoteController(NoteService service) { this.service = service; }

  @GetMapping
  public List<NoteDTO> list(@RequestParam(required = false) Instant updatedSince) {
    return service.list(updatedSince);
  }

  @GetMapping("/{id}")
  public NoteDTO get(@PathVariable String id) { return service.get(id); }

  @PostMapping
  public NoteDTO create(@RequestBody NoteDTO dto) {
    return service.upsert(UUID.randomUUID().toString(), dto);
  }

  @PutMapping("/{id}")
  public NoteDTO update(@PathVariable String id, @RequestBody NoteDTO dto) {
    return service.upsert(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String id) { service.softDelete(id); }
}
