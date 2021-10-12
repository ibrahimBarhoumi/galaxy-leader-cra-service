package com.lovotech.fr.gxld.cra.repositories;

import com.lovotech.fr.gxld.core.bean.cra.domain.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends GenericRepository<UserProfile> {
    UserProfile findByAuthUserId(Long id);
    List<UserProfile> findTop5ByNameContainingIgnoreCase (String name);
}
