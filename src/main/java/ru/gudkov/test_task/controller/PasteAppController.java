package ru.gudkov.test_task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.gudkov.test_task.dto.PasteDto;
import ru.gudkov.test_task.services.PasteCRUDService;

import java.util.Collection;

@RestController
@RequestMapping("/my-app-pastebin.home")
@RequiredArgsConstructor
public class PasteAppController {
    private final PasteCRUDService service;

    @Value("${response.message.url}")
    private String urlMessage;

    @GetMapping("/")
    private Collection<?> getPasteListOnAllUser() {
        return service.getPasteList();
    }

    @GetMapping("/{hash}")
    private String getPasteByLink(@PathVariable("hash") String hash) {
        return service.getByLink(hash);
    }

    @PostMapping
    private String createNewPaste(@RequestBody PasteDto item) {
        return urlMessage + service.create(item);
    }
}
