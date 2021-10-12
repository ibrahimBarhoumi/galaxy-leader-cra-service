package com.lovotech.fr.gxld.cra;

import com.lovotech.fr.gxld.core.bean.cra.domain.AuthUser;
import com.lovotech.fr.gxld.core.bean.cra.domain.PublicHoliday;
import com.lovotech.fr.gxld.cra.client.PublicHolidaysClient;
import com.lovotech.fr.gxld.cra.controllers.PublicHolidayController;
import com.lovotech.fr.gxld.cra.repositories.PublicHolidayRepository;
import com.lovotech.fr.gxld.cra.services.PublicHolidaysService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicHolidayTest {

    public static final String CURRENT_YEAR = "2021";

    @InjectMocks
    private PublicHolidaysService publicHolidaysServiceUnderTest;
    @Mock
    private PublicHolidayRepository publicHolidayRepository;
    @Mock
    PublicHolidaysClient publicHolidaysClient;
    @Mock
    PublicHolidayController publicHolidayController;

    public Map<LocalDate, String> holidays = new HashMap<>();

    @BeforeEach
    void init() {
        publicHolidaysServiceUnderTest = new PublicHolidaysService(publicHolidayRepository, publicHolidaysClient);
        MockitoAnnotations.initMocks(this);
        holidays.put(LocalDate.of(2021, 1, 1), "1er janvier");
        holidays.put(LocalDate.of(2021, 4, 5), "1er mai");
        holidays.put(LocalDate.of(2021, 5, 8), "Lundi de Pâques");
        holidays.put(LocalDate.of(2021, 7, 14), "14 juillet");
        holidays.put(LocalDate.of(2021, 8, 15), "Assomption");
        holidays.put(LocalDate.of(2021, 11, 1), "Toussaint");
        holidays.put(LocalDate.of(2021, 12, 25), "Jour de Noël");
    }

    @Test
    @DisplayName("should_return_public_holidays_map_success")
    void should_return_public_holidays_map_success() {
        when(publicHolidaysClient.getAllPublicHolidays(CURRENT_YEAR)).thenReturn(holidays);
        Assertions.assertEquals(holidays, publicHolidaysClient.getAllPublicHolidays(CURRENT_YEAR));
    }

    @Test
    @DisplayName("should_return_saved_holidays_success")
    void should_return_saved_holidays_success() {
        when(publicHolidayController.synchronisingHolidays()).thenReturn(ResponseEntity.ok(HttpStatus.OK));
        Assertions.assertEquals(ResponseEntity.ok(HttpStatus.OK), publicHolidayController.synchronisingHolidays());

    }
}
