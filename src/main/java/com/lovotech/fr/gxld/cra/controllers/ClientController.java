package com.lovotech.fr.gxld.cra.controllers;

import com.lovotech.fr.gxld.core.bean.cra.domain.Client;
import com.lovotech.fr.gxld.cra.repositories.ClientRepository;
import com.lovotech.fr.gxld.cra.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController extends GenericController<Client>{
    public final ClientService clientService;


    public ClientController(ClientRepository clientRepository,ClientService clientService) {
        super(clientRepository);
        this.clientService = clientService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Client>> searchByCompanyName(
            @RequestParam(defaultValue = "") String keyword
    ) {
        return ResponseEntity.ok().body(clientService.getByCompanyName(keyword));
    }
}
