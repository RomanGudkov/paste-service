package ru.gudkov.test_task.value_generate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashGenerateTest {

    @Test
    @DisplayName("Get HEX number")
    public void givenNumberInt_whenGetValue_thenHEX() {
        HashGenerate hashGenerate = new HashGenerate();
        int testNumber = 123456789;
        String expectedValue = "75BCD15";

        hashGenerate.setHash(testNumber);
        assertEquals(expectedValue.toLowerCase(), hashGenerate.getValue());
    }
}
