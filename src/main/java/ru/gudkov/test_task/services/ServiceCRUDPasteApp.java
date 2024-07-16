package ru.gudkov.test_task.services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gudkov.test_task.dto.Paste;
import ru.gudkov.test_task.enums.EnumAccessToPaste;
import ru.gudkov.test_task.enums.EnumExpirationTime;

import java.time.LocalDateTime;
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
        log.info("get list method called");
        if (pasteStorage.isEmpty()) {
            throw new RuntimeException("Please added paste");
        }
        List<String> pasteList = pasteStorage.entrySet().stream()
                .sorted(Map.Entry.<String, Paste>comparingByKey().reversed())
                .map(Map.Entry::getValue)
                .filter(p -> p.getAccess().equals("unlisted"))
                .map(Paste::getPasteBody)
                .limit(10)
                .collect(Collectors.toList());
        return pasteList;
    }

    @Override
    public Paste getPasteByLink(String hash) {
        log.info("get by link method called");
        return pasteStorage.get(hash);
    }

    @Override
    public Paste create(String body, String time, String access) {
        log.info("create method called");
        Paste newPasteDto = addNewPaste(body, time, access);
        itemHash = String.valueOf(newPasteDto.hashCode());
        pasteStorage.put(itemHash, newPasteDto);
        return newPasteDto;
    }

    private Paste addNewPaste(String body, String time, String access) {
        Paste newPaste = new Paste();
        newPaste.setPasteBody(body);
        newPaste.setExpirationTime(getExpirationDate(time));
        newPaste.setAccess(getAccessENUM(access));
        return newPaste;
    }

    private LocalDateTime getExpirationDate(String time) {
        EnumExpirationTime expirationTime = EnumExpirationTime.valueOf(time);
        return expirationTime.getExpirationValue();
    }

    private String getAccessENUM(String access) {
        EnumAccessToPaste enumAccessToPaste = EnumAccessToPaste.valueOf(access);
        return enumAccessToPaste.toString();
    }
}


























