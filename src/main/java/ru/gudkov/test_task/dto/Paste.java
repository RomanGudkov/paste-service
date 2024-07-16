package ru.gudkov.test_task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Paste {

    private String pasteBody;
    private LocalDateTime expirationTime;
    private String access;
}
