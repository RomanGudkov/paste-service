package ru.gudkov.test_task.services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gudkov.test_task.dto.Paste;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Slf4j
@Service
@NoArgsConstructor
public class ServiceCRUDPasteApp implements CRUDPasteApp<Paste> {

    static private final Map<String, Paste> pasteStorage = new LinkedHashMap<>();
    private String itemHash;

    @Override
    public Collection getPasteList() {
        if (pasteStorage.isEmpty()) {
            throw new RuntimeException("Please added paste");
        }
        List<String> pasteList = pasteStorage.entrySet().stream()
                .sorted(Map.Entry.<String, Paste>comparingByKey().reversed())
                .limit(10)
                .map(Map.Entry::getValue)
                .map(Paste::getPasteBody)
                .collect(Collectors.toList());
        return pasteList;
    }

    @Override
    public Paste getPasteByLink(String hash) {
        return pasteStorage.get(hash);
    }

    @Override
    public Paste create(Paste paste) {
        Paste newPasteSto = pasteToDto(paste);
        itemHash = String.valueOf(newPasteSto.hashCode());
        pasteStorage.put(itemHash, newPasteSto);
        log.info(itemHash);
        return newPasteSto;
    }

    private Paste pasteToDto(Paste paste) {
        Paste newPaste = new Paste();
        newPaste.setPasteBody(paste.getPasteBody());
        return newPaste;
    }
}


























