package com.example.notes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class NoteRequest {
    private String title;
    private String content;
    private List<String> tags;
}
