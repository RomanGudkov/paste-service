package ru.gudkov.test_task.enums;

import lombok.Getter;

@Getter
public enum AccessToPasteENUM {
    PUBLIC("public"),
    UNLISTED("unlisted");

    private final String accessStatus;

    AccessToPasteENUM(String accessStatus) {
        this.accessStatus = accessStatus;
    }
}
