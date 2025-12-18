package com.budgetapp.budgetapp.dto;

import com.budgetapp.budgetapp.entity.Note;

public class NoteDTO {

    private Long id;
    private String title;
    private String content;
    private String folder;

    public NoteDTO(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.folder = note.getFolder() != null ? note.getFolder() : "Uncategorized";
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getFolder() {
        return folder;
    }
}
