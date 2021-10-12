package com.lovotech.fr.gxld.cra.repositories;

import com.lovotech.fr.gxld.core.bean.cra.domain.PublicHoliday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PublicHolidayRepository extends GenericRepository<PublicHoliday> {
    Page<PublicHoliday> findAllByDateBetweenOrderByDateAsc(Pageable pageable, LocalDate beginDate, LocalDate endDate);

    List<PublicHoliday> findAllByDateBetweenOrderByDateAsc(LocalDate beginDate, LocalDate endDate);

    boolean existsByDate(LocalDate date);
}
