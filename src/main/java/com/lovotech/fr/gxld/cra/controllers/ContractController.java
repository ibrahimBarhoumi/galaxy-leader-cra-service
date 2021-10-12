package com.lovotech.fr.gxld.cra.controllers;

import com.lovotech.fr.gxld.core.bean.cra.domain.Contract;
import com.lovotech.fr.gxld.cra.repositories.ContractRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contract")
public class ContractController extends GenericController<Contract>{

    public ContractController (ContractRepository repository) {
        super(repository);
    }
}
