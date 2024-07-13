package ru.gudkov.test_task.repository;

import lombok.Getter;
import lombok.Setter;
import ru.gudkov.test_task.dto.Paste;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class RepositoryPaste {
    Paste paste;
    private final Map<String, Paste> storagePaste = new HashMap<>();

    private void savePasteOnLisT() {
//        storagePaste.put(paste.getPaste(
    }
}
