package ru.gudkov.test_task.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.gudkov.test_task.dto.PasteDto;
import ru.gudkov.test_task.services.PasteCRUDService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/my-app-pastebin.home")
public class PasteAppController {
    private final PasteCRUDService service;

    @Value("${response.message_url}")
    private String urlMessage;
    @Value("${response.message_empty}")
    private String emptyMessage;
    @Value("${response.message_expired}")
    private String expiredMessage;


    public PasteAppController(PasteCRUDService service) {
        this.service = service;
    }

    @GetMapping("/")
    private Collection<?> getPasteListOnAllUser() {
        return service.getPasteList() != null ?
                service.getPasteList() :
                new ArrayList<>(Collections.singleton(emptyMessage));
    }

    @GetMapping("/{hash}")
    private String getPasteByLink(@PathVariable("hash") String hash) {
        return service.getByLink(hash) != null ?
                service.getByLink(hash).getPasteBody() : expiredMessage;
    }

    @PostMapping
    private String createNewPaste(@RequestBody PasteDto item) {
        PasteDto pasteDto = service.create(item);
        return urlMessage + pasteDto.getHash();
    }
}
