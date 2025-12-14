package com.budgetapp.budgetapp.controller;

import com.budgetapp.budgetapp.entity.Note;
import com.budgetapp.budgetapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> getNotes(@RequestHeader("token") String token) {
        List<Note> notes = noteService.getNotesByToken(token);
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody Note note, @RequestHeader("token") String token) {
        noteService.createNote(note, token);
        return ResponseEntity.ok("Note created successfully.");
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id, @RequestHeader("token") String token) {
        noteService.deleteNote(id, token);
        return ResponseEntity.ok("Note deleted successfully.");
    }
}
