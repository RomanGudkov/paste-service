package ru.gudkov.test_task.enums;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public enum ExpirationTimeENUM {

    MINUTES_10(LocalDateTime.now().plusMinutes(10)),
    HOURS_1(LocalDateTime.now().plusHours(1)),
    HOURS_3(LocalDateTime.now().plusHours(3)),
    DAY_1(LocalDateTime.now().plusDays(1)),
    WEEK_1(LocalDateTime.now().plusWeeks(1)),
    MONTH_1(LocalDateTime.now().plusMonths(1)),
    FOREVER(LocalDateTime.now().plusYears(1000));

    private final LocalDateTime expirationValue;

    ExpirationTimeENUM(LocalDateTime expirationValue) {
        this.expirationValue = expirationValue;
    }
}