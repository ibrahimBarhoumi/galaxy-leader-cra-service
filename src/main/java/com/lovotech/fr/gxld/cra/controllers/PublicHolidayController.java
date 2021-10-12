package com.lovotech.fr.gxld.cra.controllers;

import com.lovotech.fr.gxld.core.bean.cra.DTO.PageDTO;
import com.lovotech.fr.gxld.core.bean.cra.domain.PublicHoliday;
import com.lovotech.fr.gxld.cra.repositories.PublicHolidayRepository;
import com.lovotech.fr.gxld.cra.services.PublicHolidaysService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/publicholidays")
public class PublicHolidayController extends GenericController<PublicHoliday> {

    private final PublicHolidaysService publicHolidaysService;

    public PublicHolidayController(PublicHolidayRepository publicHolidayRepository,
                                   PublicHolidaysService publicHolidaysService) {
        super(publicHolidayRepository);
        this.publicHolidaysService = publicHolidaysService;
    }

    @Override
    @GetMapping("")
    public ResponseEntity<PageDTO<PublicHoliday>> getPage(Pageable pageable, @RequestParam Map<String,String> inputs){
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("date"));
        inputs.remove("page");
        inputs.remove("size");
        return ResponseEntity.ok(this.publicHolidaysService.getAllByFilter(inputs,pageable));
    }

    @GetMapping("/json/{year}")
    public ResponseEntity<Map<LocalDate, String>> getJson(@PathVariable(value = "year") String year) {
        return ResponseEntity.ok(publicHolidaysService.getPublicHolidays(year));
    }

    @GetMapping("/sync")
    public ResponseEntity<HttpStatus> synchronisingHolidays() {
        publicHolidaysService.syncPublicHolidays();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
