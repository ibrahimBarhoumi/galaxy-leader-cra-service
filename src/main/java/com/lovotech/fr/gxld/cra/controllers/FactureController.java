package com.lovotech.fr.gxld.cra.controllers;

import com.lovotech.fr.gxld.core.bean.cra.domain.Facture;
import com.lovotech.fr.gxld.cra.repositories.GenericRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facture")
public class FactureController extends GenericController<Facture>{
    public FactureController(GenericRepository<Facture> repository) {
        super(repository);
    }
}
