package com.budgetapp.budgetapp.repository;

import com.budgetapp.budgetapp.entity.Note;
import com.budgetapp.budgetapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUser(User user);
}
