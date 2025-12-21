package com.budgetapp.budgetapp.controller;

import com.budgetapp.budgetapp.dto.NoteDTO;
import com.budgetapp.budgetapp.entity.Note;
import com.budgetapp.budgetapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping
    public List<NoteDTO> getAllNotes() {
        return noteRepository.findAll()
                .stream()
                .map(NoteDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public NoteDTO getNoteById(@PathVariable Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        return new NoteDTO(note);
    }

    @PostMapping
    public NoteDTO createNote(@RequestBody Note note) {
        Note saved = noteRepository.save(note);
        return new NoteDTO(saved);
    }

    @PutMapping("/{id}")
    public NoteDTO updateNote(
            @PathVariable Long id,
            @RequestBody Note noteDetails
    ) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        note.setFolder(noteDetails.getFolder());

        Note saved = noteRepository.save(note);
        return new NoteDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteRepository.deleteById(id);
    }
}
