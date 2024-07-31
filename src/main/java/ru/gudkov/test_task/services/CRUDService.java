package ru.gudkov.test_task.services;

import ru.gudkov.test_task.dto.PasteDto;

import java.util.Collection;

public interface CRUDService<T> {

    Collection<T> getPasteList();

    T getByLink(String hash);

    T create(PasteDto object);

}
