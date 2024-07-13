package ru.gudkov.test_task.controller;

import org.springframework.web.bind.annotation.*;
import ru.gudkov.test_task.dto.Paste;
import ru.gudkov.test_task.services.ServiceCRUDPasteApp;

import java.util.Collection;

@RestController
@RequestMapping("/my-app-pastebin.home")
public class ControllerPasteApp {
    ServiceCRUDPasteApp service;

    public ControllerPasteApp(ServiceCRUDPasteApp service) {
        this.service = service;
    }

    @GetMapping("/public")
    private Collection<?> getPasteListOnAllUser() {
       return service.getPasteList();
    }

    @GetMapping("/{unlisted}")
    private String getPasteFromLinkFromUser(@PathVariable("unlisted") String hashcode) {
        return service.getPasteByLink(hashcode).getPasteBody();
    }

    @PostMapping
    private String setNewPasteFromUser(@RequestBody Paste paste) {
        service.create(paste);
        String hashCode = service.getItemHash();
        return "http:/" + "/my-app-pastebin.home/" + "/" + hashCode;
    }
}
