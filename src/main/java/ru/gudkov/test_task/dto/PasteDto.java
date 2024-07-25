package ru.gudkov.test_task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasteDto {

    private String pasteBody;
    private String expirationTime;
    private String access;
    private String hash;
}
