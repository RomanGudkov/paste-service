package ru.gudkov.test_task.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gudkov.test_task.dto.PasteDto;
import ru.gudkov.test_task.entity.Paste;
import ru.gudkov.test_task.enums.AccessToPasteENUM;
import ru.gudkov.test_task.repository.PasteRepository;
import ru.gudkov.test_task.value_generate.ExpirationGenerate;
import ru.gudkov.test_task.value_generate.HashGenerate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Slf4j
@Service
@RequiredArgsConstructor
public class PasteCRUDService implements CRUDService<String> {
    private final PasteRepository repository;
    private final HashGenerate hashGenerate;
    private final ExpirationGenerate expirationGenerate;
    @Value("${response.message.records_size}")
    private int recordsSize;
    @Value("${response.message.expired}")
    private String expiredMessage;
    @Value("${response.message.is_empty}")
    private String emptyMessage;

    @Override
    public Collection<String> getPasteList() {
        log.info("get list method called");
        PageRequest expirationTime = PageRequest.of(0, recordsSize, Sort.by("expirationTime")
                .descending());
        Page<Paste> page = repository.findAllByExpirationTimeAndAccess(LocalDateTime.now(), expirationTime);
        return repository.count() == 0 ?
                Collections.singleton(emptyMessage) : page.getContent().stream()
                .map(Paste::getPasteBody)
                .collect(Collectors.toList());
    }

    @Override
    public String getByLink(String hash) {
        log.info("get by link method called");
        Paste paste = repository.findByHashCode(hash);
        return LocalDateTime.now()
                .isAfter(paste.getExpirationTime()) ?
                expiredMessage : paste.getPasteBody();
    }

    @Override
    public String create(PasteDto pasteDto) {
        log.info("create method called");
        Paste paste = mapToEntity(pasteDto);
        repository.save(paste);
        return paste.getHashCode();
    }

    protected Paste mapToEntity(PasteDto pasteDto) {
        log.info("mapToEntity method called");
        Paste paste = new Paste();
        hashGenerate.setHash(paste.hashCode());
        expirationGenerate.setTime(pasteDto.getExpirationTime());
        paste.setPasteBody(pasteDto.getPasteBody());
        paste.setExpirationTime(expirationGenerate.getExpirationDate());
        paste.setAccess(AccessToPasteENUM.valueOf(pasteDto.getAccess()).toString());
        paste.setHashCode(hashGenerate.getValue());
        return paste;
    }
}


























