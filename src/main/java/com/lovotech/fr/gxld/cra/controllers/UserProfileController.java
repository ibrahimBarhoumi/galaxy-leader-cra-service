package com.lovotech.fr.gxld.cra.controllers;

import com.lovotech.fr.gxld.core.bean.cra.domain.UserProfile;
import com.lovotech.fr.gxld.cra.repositories.ClientRepository;
import com.lovotech.fr.gxld.cra.repositories.UserProfileRepository;
import com.lovotech.fr.gxld.cra.services.ClientService;
import com.lovotech.fr.gxld.cra.services.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userProfile")
public class UserProfileController extends GenericController<UserProfile> {
    public final UserProfileService userProfileService;
    public UserProfileController(UserProfileRepository userProfileRepository, UserProfileService userProfileService) {
        super(userProfileRepository);
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<UserProfile> getOneProfile(@PathVariable Long id){
        return ResponseEntity.ok(userProfileService.getuserProfile(id));
    }
    @GetMapping("/search")
    public ResponseEntity<List<UserProfile>> getAllProfile(@RequestParam(defaultValue = "") String name){
        return ResponseEntity.ok().body(userProfileService.getAllUserProfile(name));
    }
}
