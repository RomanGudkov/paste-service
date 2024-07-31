package ru.gudkov.test_task.value_generate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.gudkov.test_task.enums.ExpirationTimeENUM;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class ExpirationGenerateTest {

    @Test
    @DisplayName("Get expiration paste time")
    public void givenENUMType_whenGetExpirationDate_thenDateTime() {
        ExpirationTimeENUM hours1 = ExpirationTimeENUM.HOURS_1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");

        LocalDateTime expectedTime = LocalDateTime.now().plusHours(1);
        LocalDateTime actualTime = hours1.getExpirationValue();

        log.info(actualTime.format(formatter));
        assertEquals(expectedTime.format(formatter), actualTime.format(formatter));
    }
}
