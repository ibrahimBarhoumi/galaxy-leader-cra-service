package com.lovotech.fr.gxld.cra.services;

import com.lovotech.fr.gxld.core.bean.cra.domain.UserProfile;


import com.lovotech.fr.gxld.cra.repositories.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileService extends GenericService<UserProfile>{

    private final UserProfileRepository userProfileRepository;
    public UserProfileService( UserProfileRepository userProfileRepository) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
    }
    public UserProfile getuserProfile(Long id){
        return userProfileRepository.findByAuthUserId(id);
    }

    public List<UserProfile> getAllUserProfile(String name){
        if (name !="")
            return userProfileRepository.findTop5ByNameContainingIgnoreCase(name);
        else
            return new ArrayList<>();
    }
}
