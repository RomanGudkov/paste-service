package ru.gudkov.test_task.controller;

import org.springframework.web.bind.annotation.*;
import ru.gudkov.test_task.dto.PasteDto;
import ru.gudkov.test_task.services.PasteCRUDService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/my-app-pastebin.home")
public class PasteAppController {
    PasteCRUDService service;

    public PasteAppController(PasteCRUDService service) {
        this.service = service;
    }

    @GetMapping("/")
    private Collection<?> getPasteListOnAllUser() {
        return service.getPasteList() != null ?
                service.getPasteList() :
                new ArrayList<>(Collections.singleton("List is empty"));
    }

    @GetMapping("/{hash}")
    private String getPasteByLink(@PathVariable("hash") Long hashcode) {
        return service.getByLink(hashcode) != null ?
                service.getByLink(hashcode).getPasteBody() : "The paste has expired";
    }

    @PostMapping
    private String createNewPaste(@RequestBody PasteDto pasteDto) {
        service.create(pasteDto);
        String hashCode = String.valueOf(pasteDto.hashCode());
        return "http://my-app-pastebin.home/" + hashCode;
    }
}
