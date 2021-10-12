package com.lovotech.fr.gxld.cra.repositories;

import com.lovotech.fr.gxld.core.bean.cra.domain.Timesheet;
import com.lovotech.fr.gxld.cra.repositories.spec.TimesheetSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long>,JpaSpecificationExecutor<Timesheet> {
    Page<Timesheet> getByDate(LocalDate date,Pageable pageable);
    Page<Timesheet> getByDate(TimesheetSpecification spec,LocalDate date,Pageable pageable);

    Optional<Timesheet> getTimeSheetByUserProfileIdAndDate(Long userProfileId, LocalDate date);
    Page<Timesheet> findAllByDate(Specification specification, LocalDate date, Pageable pageable);


}
