package com.lovotech.fr.gxld.cra.services;

import com.lovotech.fr.gxld.core.bean.cra.DTO.PageDTO;
import com.lovotech.fr.gxld.core.bean.cra.domain.PublicHoliday;
import com.lovotech.fr.gxld.cra.client.PublicHolidaysClient;
import com.lovotech.fr.gxld.cra.repositories.PublicHolidayRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class PublicHolidaysService extends GenericService<PublicHoliday> {

    public static final int CURRENT_YEAR = LocalDate.now().getYear();
    public static final int FIRST_MONTH = 01;
    public static final int FIRST_DAY = 01;
    public static final int LAST_MONTH = 12;
    public static final int LAST_DAY = 31;
    private final PublicHolidayRepository publicHolidaysRepository;
    private final PublicHolidaysClient publicHolidaysClient;

    public PublicHolidaysService(PublicHolidayRepository holidaysRepository, PublicHolidaysClient publicHolidaysClient) {
        super(holidaysRepository);
        this.publicHolidaysRepository = holidaysRepository;
        this.publicHolidaysClient = publicHolidaysClient;
    }

    @Override
    public PageDTO<PublicHoliday> getPage(Pageable pageable) {
        return new PageDTO<>(publicHolidaysRepository.findAllByDateBetweenOrderByDateAsc(pageable,
                LocalDate.of(CURRENT_YEAR, FIRST_MONTH, FIRST_DAY),
                LocalDate.of(CURRENT_YEAR, LAST_MONTH, LAST_DAY)));
    }

    public Map<LocalDate, String> getPublicHolidays(String year) {
        return publicHolidaysClient.getAllPublicHolidays(year);
    }

    public void syncPublicHolidays() {
        Map<LocalDate, String> holidays = this.getPublicHolidays(String.valueOf(LocalDate.now().getYear()));
        holidays.forEach((date, label) -> {
            if (!publicHolidaysRepository.existsByDate(date)) {
                var holiday = new PublicHoliday();
                holiday.setDate(date);
                holiday.setLabel(label);
                this.create(holiday);
            }
        });
    }
}
