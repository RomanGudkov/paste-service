package ru.gudkov.test_task.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class PasteCRUDService implements CRUDService<PasteDto> {
    private final PasteRepository repository;
    private final HashGenerate hashGenerate;
    private final ExpirationGenerate expirationGenerate;

    @Override
    public Collection getPasteList() {
        log.info("get list method called");
        PageRequest expirationTime = PageRequest.of(0, 10, Sort.by("expirationTime")
                .descending());
        Page<Paste> page = repository.findAllByExpirationTimeAndAccess(LocalDateTime.now(), expirationTime);
        return repository.count() == 0 ?
                null : page.getContent().stream()
                .map(Paste::getPasteBody)
                .collect(Collectors.toList());
    }

    @Override
    public PasteDto getByLink(String hash) {
        log.info("get by link method called");
        Paste byHashCode = repository.findByHashCode(hash);
        return LocalDateTime.now()
                .isAfter(byHashCode.getExpirationTime()) ?
                null : mapToDto(byHashCode);
    }

    @Override
    public PasteDto create(PasteDto pasteDto) {
        log.info("create method called");
        Paste paste = mapToEntity(pasteDto);
        repository.save(paste);
        return mapToDto(paste);
    }

    private PasteDto mapToDto(Paste paste) {
        PasteDto newPasteDto = new PasteDto();
        newPasteDto.setPasteBody(paste.getPasteBody());
        newPasteDto.setAccess(paste.getAccess());
        newPasteDto.setHash(paste.getHashCode());
        return newPasteDto;
    }

    private Paste mapToEntity(PasteDto pasteDto) {
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


























