package com.lovotech.fr.gxld.cra.controllers;

import com.lovotech.fr.gxld.core.bean.cra.domain.AuthRole;
import com.lovotech.fr.gxld.cra.repositories.AuthRoleRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class AuthRoleController extends GenericController<AuthRole> {
    public AuthRoleController(AuthRoleRepository repository) {
        super(repository);
    }
}
