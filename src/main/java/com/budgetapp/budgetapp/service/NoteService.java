package com.budgetapp.budgetapp.service;

import com.budgetapp.budgetapp.entity.Note;
import com.budgetapp.budgetapp.entity.User;
import com.budgetapp.budgetapp.repository.NoteRepository;
import com.budgetapp.budgetapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    public void createNote(Note note, String token) {
        Optional<User> optionalUser = userRepository.findByAccessToken(token);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid token"); // token 無效
        }

        note.setUser(optionalUser.get());
        noteRepository.save(note);
    }

    public List<Note> getNotesByToken(String token) {
        Optional<User> optionalUser = userRepository.findByAccessToken(token);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid token"); // token 無效
        }

        return noteRepository.findAllByUser(optionalUser.get());
    }

    public void deleteNote(Long id, String token) {
        Optional<User> optionalUser = userRepository.findByAccessToken(token);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }

        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUser().getId().equals(optionalUser.get().getId())) {
            throw new RuntimeException("Cannot delete another user's note");
        }

        noteRepository.delete(note);
    }
}
