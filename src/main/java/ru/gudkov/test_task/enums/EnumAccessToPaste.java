package ru.gudkov.test_task.enums;

import lombok.Getter;

@Getter
public enum EnumAccessToPaste {
    PUBLIC("public"),
    UNLISTED("unlisted");

    private final String accessStatus;

    EnumAccessToPaste(String accessStatus) {
        this.accessStatus = accessStatus;
    }
}
