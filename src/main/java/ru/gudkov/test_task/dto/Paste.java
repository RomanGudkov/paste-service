package ru.gudkov.test_task.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Paste {

    private String pasteBody;
    private LocalDateTime timeCreate;

    public Paste() {
        this.timeCreate = LocalDateTime.now();
    }
}
