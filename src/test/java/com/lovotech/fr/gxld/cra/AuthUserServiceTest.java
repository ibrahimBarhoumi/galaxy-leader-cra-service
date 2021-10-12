package com.lovotech.fr.gxld.cra;

import com.lovotech.fr.gxld.core.bean.cra.domain.AuthRole;
import com.lovotech.fr.gxld.core.bean.cra.domain.AuthUser;
import com.lovotech.fr.gxld.cra.repositories.AuthUserRepository;
import com.lovotech.fr.gxld.cra.services.AuthUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthUserServiceTest {


    @InjectMocks
    private AuthUserService authUserServiceUnderTest;
    @Mock
    private AuthUserRepository authUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    public static final String USERNAME = "admin";

    public static final String PASSWORD = "MyPassword";
    public static final String ENCODED_PASSWORD = "$2a$10$WC8E70GJhEyvoD3zcQ5ML.D.MZz/lumIgWB1N6alYdjQs1ZSNWz1a";
    public static final String MAIL_ADDRESS = "admin.admin@galaxy.com";
    public static final String MOBILE = "+21625568344";
    public static final String ROLE_NAME = "ROLE_ADMIN";
    public static final String DESCRIPTION = "Admin User - Has permission to perform admin tasks";


    @BeforeEach
    void init() {
        authUserServiceUnderTest = new AuthUserService(authUserRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("should_return_saved_user_success")
    public void should_return_saved_user_success() {
        when(passwordEncoder.encode(any())).thenReturn(ENCODED_PASSWORD);
        doReturn(buildAuthUserWithHashedPassword()).when(authUserRepository).save(any(AuthUser.class));
        Assertions.assertEquals(buildAuthUserWithHashedPassword(), authUserServiceUnderTest.create(buildAuthUser()));
    }

    private AuthUser buildAuthUser() {
        return AuthUser.builder()
                .userName(USERNAME)
                .email(MAIL_ADDRESS)
                .password(PASSWORD)
                .enabled(true)
                .roles(Collections.singletonList(buildAuthRole()))
                .build();

    }

    private AuthUser buildAuthUserWithHashedPassword() {
        return AuthUser.builder()
                .userName(USERNAME)
                .email(MAIL_ADDRESS)
                .password(ENCODED_PASSWORD)
                .enabled(true)
                .roles(Collections.singletonList(buildAuthRole()))
                .build();

    }

    private AuthRole buildAuthRole() {
        return AuthRole.builder()
                .name(ROLE_NAME)
                .description(DESCRIPTION)
                .build();
    }
}
