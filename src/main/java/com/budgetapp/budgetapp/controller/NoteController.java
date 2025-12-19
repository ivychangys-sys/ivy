package com.budgetapp.budgetapp.controller;

import com.budgetapp.budgetapp.dto.NoteDTO;
import com.budgetapp.budgetapp.entity.Note;
import com.budgetapp.budgetapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NoteDTO getNoteById(@PathVariable Long id) {
        Note note = noteRepository.findById(id).orElseThrow();
        return new NoteDTO(note);
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteRepository.save(note);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable Long id, @RequestBody Note noteDetails) {
        Note note = noteRepository.findById(id).orElseThrow();
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        note.setFolder(noteDetails.getFolder());
        return noteRepository.save(note);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteRepository.deleteById(id);
    }
}
