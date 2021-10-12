package com.lovotech.fr.gxld.cra.controllers;

import com.lovotech.fr.gxld.core.bean.cra.DTO.PageDTO;
import com.lovotech.fr.gxld.core.bean.cra.domain.AuthUser;
import com.lovotech.fr.gxld.core.bean.cra.domain.Timesheet;
import com.lovotech.fr.gxld.cra.repositories.AuthUserRepository;
import com.lovotech.fr.gxld.cra.services.TimesheetService;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/timesheets")
public class TimesheetController {

    private final TimesheetService timesheetService;
    private final AuthUserRepository authUserRepository;

    public TimesheetController(TimesheetService timesheetService,AuthUserRepository authUserRepository) {
        this.timesheetService = timesheetService;
        this.authUserRepository = authUserRepository;
    }

    @GetMapping("/{date}") // TODO : use when we get connected user in cra
    public ResponseEntity<PageDTO<Timesheet>> getByDate(@RequestParam Map<String,String> inputs,@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,Pageable pageable) {
        inputs.remove("size");
        inputs.remove("page");
        return ResponseEntity.ok(timesheetService.getAllByFilter(inputs,pageable,date));
    }

    @PostMapping
    @PutMapping
    public ResponseEntity<Timesheet> saveOrUpdate(@RequestBody Timesheet timesheet) {
        return ResponseEntity.ok(timesheetService.saveOrUpdate(timesheet));
    }

    @GetMapping("/{username}/{date}")
    public ResponseEntity<Timesheet> getOrCreateTimesheet(@PathVariable String username,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Timesheet res;
        Optional<AuthUser> authUser = authUserRepository.findByUserName(username);
        Optional<Timesheet> timesheet = timesheetService.getTimeSheetByUserIdAndDate(authUser.get().getId(), date);
        // TODO use JAVA 8/11
        if (timesheet.isEmpty()) {
            res = timesheetService.initTimesheet(authUser.get().getId(), date);
        } else {
            res = timesheet.get();
        }
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

}
