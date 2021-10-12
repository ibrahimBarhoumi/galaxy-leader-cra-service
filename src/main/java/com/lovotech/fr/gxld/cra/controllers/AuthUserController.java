package com.lovotech.fr.gxld.cra.controllers;

import com.lovotech.fr.gxld.core.bean.cra.DTO.PageDTO;
import com.lovotech.fr.gxld.core.bean.cra.domain.AuthUser;
import com.lovotech.fr.gxld.cra.repositories.AuthUserRepository;
import com.lovotech.fr.gxld.cra.services.AuthUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthUserController extends GenericController<AuthUser> {

    private final AuthUserService authUserService;

    public AuthUserController(AuthUserRepository authUserRepository, AuthUserService authUserService) {
        super(authUserRepository);
        this.authUserService = authUserService;
    }

    @PutMapping
    public ResponseEntity<AuthUser> update(@RequestBody AuthUser updated) {
        return ResponseEntity.ok(authUserService.update(updated));
    }

    @PostMapping
    public ResponseEntity<AuthUser> create(@RequestBody AuthUser created) {
        return ResponseEntity.ok(authUserService.create(created));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        authUserService.delete(id);
        return ResponseEntity.ok("Ok");
    }
    @GetMapping("/search")
    public ResponseEntity<PageDTO<AuthUser>> searchByCompanyName(
            Pageable pageable ,@RequestParam String keyword
    ) {
        return ResponseEntity.ok().body(authUserService.search(pageable,keyword,keyword));
    }

}
