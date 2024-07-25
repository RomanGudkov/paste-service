package ru.gudkov.test_task.value_generate;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.gudkov.test_task.enums.ExpirationTimeENUM;

import java.time.LocalDateTime;

@Component
@Setter
@NoArgsConstructor
public class ExpirationGenerate {
    private String time;

    public LocalDateTime getExpirationDate() {
        return ExpirationTimeENUM.valueOf(time).getExpirationValue();
    }
}
