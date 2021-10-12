package com.lovotech.fr.gxld.cra.services;


import com.lovotech.fr.gxld.core.bean.cra.DTO.PageDTO;
import com.lovotech.fr.gxld.core.bean.cra.domain.AuthUser;
import com.lovotech.fr.gxld.cra.repositories.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthUserService extends GenericService<AuthUser> {

    private AuthUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthUserService(AuthUserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository ;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    public AuthUser create(AuthUser newDomain) {
        AuthUser dbDomain = newDomain.createNewInstance();
        dbDomain.setPassword(passwordEncoder.encode(dbDomain.getPassword()));
        dbDomain.setEnabled(true);
        return userRepository.save(dbDomain);
    }

    @Transactional
    public AuthUser update(AuthUser updated){
        AuthUser dbDomain = get(updated.getId());
        dbDomain.update(updated);
        dbDomain.setPassword(passwordEncoder.encode(dbDomain.getPassword()));
        return userRepository.save(dbDomain);
    }

    @Transactional
    public void delete(Long id){
        //check if object with this id exists
        AuthUser toDelete = get(id);
        toDelete.setEnabled(false);
        userRepository.save(toDelete);
    }


    public PageDTO<AuthUser> search(Pageable pageable,String keyword1 , String keyword){
        return new PageDTO<AuthUser>( userRepository.findByEmailContainingIgnoreCaseOrUserNameContainingIgnoreCase(pageable,keyword1,keyword));
    }
}
