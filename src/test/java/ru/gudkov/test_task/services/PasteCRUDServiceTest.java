package ru.gudkov.test_task.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import ru.gudkov.test_task.dto.PasteDto;
import ru.gudkov.test_task.entity.Paste;
import ru.gudkov.test_task.repository.PasteRepository;
import ru.gudkov.test_task.value_generate.ExpirationGenerate;
import ru.gudkov.test_task.value_generate.HashGenerate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@Slf4j
public class PasteCRUDServiceTest {

    private final PasteRepository repository = Mockito.mock(PasteRepository.class);
    private final HashGenerate hashGenerate = Mockito.mock(HashGenerate.class);
    private final ExpirationGenerate expirationGenerate = Mockito.mock(ExpirationGenerate.class);
    private static final String EXPECTED_HASH = "75BCD15";
    private static final String EXPECTED_BODY = "test recording";
    private static final String EXPECTED_ACCESS = "PUBLIC";
    private static final LocalDateTime DATE_TIME = LocalDateTime.now().plusHours(1);
    private PasteCRUDService service;
    @Value("${response.message.expired}")
    private String expiredMessage;
    @Value("${response.message.records_size}")
    private int recordsSize;

    @BeforeEach
    public void setUp() {
        service = new PasteCRUDService(repository, hashGenerate, expirationGenerate);
        when(hashGenerate.getValue()).thenReturn(EXPECTED_HASH);
        when(expirationGenerate.getExpirationDate()).thenReturn(DATE_TIME);
    }

    @Test
    @DisplayName("get paste list")
    public void givenRequest_whenGetPasteList_thenCollection() {
        List<Paste> pasteList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Paste paste = createPasteEntity();
            paste.setPasteBody(String.valueOf(i));
            paste.setExpirationTime(LocalDateTime.now());
            pasteList.add(paste);
        }
        Pageable pageable = PageRequest.of(0, 9);
        Page<Paste> page = new PageImpl<>(pasteList, pageable, 1);

        when(repository.findAllByExpirationTimeAndAccess(Mockito.any(LocalDateTime.class)
                , Mockito.any(PageRequest.class))).thenReturn(page);
        boolean size = service.getPasteList().size() == recordsSize;

        assertTrue(size); // java.lang.IllegalArgumentException: Page size must not be less than one
    }

    @Test
    @DisplayName("get paste by link method")
    public void givenHash_whenGetByLink_thenGetPasteDtoObject() {
        Paste pasteIsBeforeTime = createPasteEntity();
        when(repository.findByHashCode(EXPECTED_HASH)).thenReturn(pasteIsBeforeTime);
        assertEquals(EXPECTED_BODY, service.getByLink(pasteIsBeforeTime.getHashCode()));

        Paste pasteIsAfterTime = createPasteEntity();
        pasteIsAfterTime.setExpirationTime(LocalDateTime.now().minusHours(1));
        when(repository.findByHashCode(EXPECTED_HASH)).thenReturn(pasteIsAfterTime);
        assertEquals(expiredMessage, service.getByLink(pasteIsAfterTime.getHashCode()));

        verify(repository, times(2)).findByHashCode(Mockito.any());
    }

    @Test
    @DisplayName("create method")
    public void givenPasteDtoObject_whenCreate_thenAddPasteEntityToRepository() {
        PasteDto pasteDto = createPasteDtoObject();
        Paste paste = createPasteEntity();
        when(repository.save(Mockito.any(Paste.class))).thenReturn(paste);

        assertEquals(EXPECTED_HASH, service.create(pasteDto));
        assertEquals(EXPECTED_HASH, repository.save(paste).getHashCode());
    }

    @Test
    @DisplayName("dto to entity")
    public void givenDtoObject_whenMapToEntity_thenReturnPasteEntity() {
        PasteDto pasteDto = createPasteDtoObject();

        assertEquals(EXPECTED_HASH, service.mapToEntity(pasteDto).getHashCode());
        assertEquals(EXPECTED_BODY, service.mapToEntity(pasteDto).getPasteBody());
        assertEquals(EXPECTED_ACCESS, service.mapToEntity(pasteDto).getAccess());
        assertEquals(DATE_TIME, service.mapToEntity(pasteDto).getExpirationTime());

        verify(hashGenerate, atLeastOnce()).getValue();
        verify(expirationGenerate, atLeastOnce()).getExpirationDate();
    }

    private PasteDto createPasteDtoObject() {
        PasteDto pasteDto = new PasteDto();
        pasteDto.setPasteBody(EXPECTED_BODY);
        pasteDto.setAccess(EXPECTED_ACCESS);
        pasteDto.setHash(hashGenerate.getValue());
        pasteDto.setExpirationTime(expirationGenerate.getExpirationDate().toString());
        return pasteDto;
    }

    private Paste createPasteEntity() {
        Paste paste = new Paste();
        paste.setPasteBody(EXPECTED_BODY);
        paste.setExpirationTime(expirationGenerate.getExpirationDate());
        paste.setAccess(EXPECTED_ACCESS);
        paste.setHashCode(hashGenerate.getValue());
        return paste;
    }
}
