package com.budgetapp.budgetapp.dto;

import com.budgetapp.budgetapp.entity.Note;

public class NoteDTO {

    private String key;

    private String label;

    private String data;

    private String folder;

    public NoteDTO(Note note) {
        this.key = String.valueOf(note.getId());
        this.label = note.getTitle();
        this.data = note.getContent();
        this.folder = note.getFolder() != null
                ? note.getFolder()
                : "Uncategorized";
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public String getData() {
        return data;
    }

    public String getFolder() {
        return folder;
    }
}
