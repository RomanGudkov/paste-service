package ru.gudkov.test_task.value_generate;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@NoArgsConstructor
public class HashGenerate {
    private int hash;

    public String getValue() {
        return Integer.toHexString(hash);
    }
}
