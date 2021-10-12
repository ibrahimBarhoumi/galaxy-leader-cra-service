package com.lovotech.fr.gxld.cra.controllers;

import java.util.List;

import com.lovotech.fr.gxld.core.bean.cra.domain.Activity;
import com.lovotech.fr.gxld.cra.repositories.GenericRepository;
import com.lovotech.fr.gxld.cra.services.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
public class ActivityController extends GenericController<Activity> {

    public final ActivityService activityService;

    public ActivityController(GenericRepository<Activity>  activityRepository, ActivityService activityService) {
        super(activityRepository);
        this.activityService = activityService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Activity>> findByClient(@RequestParam(defaultValue = "") String label) {
        return ResponseEntity.ok().body(activityService.getByLabelName(label));
    }

}
