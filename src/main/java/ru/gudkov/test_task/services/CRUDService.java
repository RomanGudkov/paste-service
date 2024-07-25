package ru.gudkov.test_task.services;

import java.util.Collection;

public interface CRUDService<T> {

    Collection<T> getPasteList();

    T getByLink(String hash);

    T create(T object);

}
