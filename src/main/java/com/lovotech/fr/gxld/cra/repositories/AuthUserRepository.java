package com.lovotech.fr.gxld.cra.repositories;

import java.util.Optional;

import com.lovotech.fr.gxld.core.bean.cra.DTO.PageDTO;
import com.lovotech.fr.gxld.core.bean.cra.domain.AuthUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;



@Repository
public interface AuthUserRepository extends GenericRepository<AuthUser>{

  Optional<AuthUser> findByUserName(String username);
 Page<AuthUser> findByEmailContainingIgnoreCaseOrUserNameContainingIgnoreCase(Pageable pageable, String keyword1 , String keyword2);
}
