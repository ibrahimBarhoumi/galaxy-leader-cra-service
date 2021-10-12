package com.lovotech.fr.gxld.cra.services;


import com.lovotech.fr.gxld.core.bean.cra.domain.Activity;
import com.lovotech.fr.gxld.cra.repositories.ActivityRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService extends GenericService<Activity>{

    private final ActivityRepository activityRepository ;
    public ActivityService(ActivityRepository activityRepository) {
        super(activityRepository);
        this.activityRepository = activityRepository ;
    }

    public List<Activity> getByLabelName(String label){
        if (label != "")
            return activityRepository.findTop5ByLabelContainingIgnoreCaseAndClientIdNotNull(label);
        else return new ArrayList<>();
    }
}
