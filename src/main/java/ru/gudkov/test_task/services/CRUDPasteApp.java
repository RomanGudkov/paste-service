package ru.gudkov.test_task.services;

import java.util.Collection;

public interface CRUDPasteApp<T> {

    Collection<T> getPasteList();

    T getPasteByLink(String hash);

    T create(T item);

}
