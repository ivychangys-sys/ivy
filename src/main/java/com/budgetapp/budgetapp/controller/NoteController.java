package com.budgetapp.budgetapp.controller;

import com.budgetapp.budgetapp.dto.NoteDTO;
import com.budgetapp.budgetapp.entity.Note;
import com.budgetapp.budgetapp.entity.User;
import com.budgetapp.budgetapp.repository.NoteRepository;
import com.budgetapp.budgetapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    // Helper method to retrieve user by token
    private User getCurrentUser(String accessToken) {
        return userRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public List<NoteDTO> getAllNotes(@RequestHeader("Authorization") String token) {
        User user = getCurrentUser(token);
        // Assuming NoteRepository has a findByUser method
        return noteRepository.findAllByUser(user)
                .stream()
                .map(NoteDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NoteDTO getNoteById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) {
        User user = getCurrentUser(token);
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Security check: Ensure the note belongs to the requesting user
        if (!note.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to this note");
        }

        return new NoteDTO(note);
    }

    @PostMapping
    public NoteDTO createNote(
            @RequestHeader("Authorization") String token,
            @RequestBody Note note
    ) {
        User user = getCurrentUser(token);
        note.setUser(user); // Set the owner of the note
        Note saved = noteRepository.save(note);
        return new NoteDTO(saved);
    }

    @PutMapping("/{id}")
    public NoteDTO updateNote(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody Note noteDetails
    ) {
        User user = getCurrentUser(token);
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Security check: Ensure the note belongs to the requesting user
        if (!note.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized update attempt");
        }

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        note.setFolder(noteDetails.getFolder());

        Note saved = noteRepository.save(note);
        return new NoteDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) {
        User user = getCurrentUser(token);
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Security check: Ensure the note belongs to the requesting user
        if (!note.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized delete attempt");
        }

        noteRepository.deleteById(id);
    }
}