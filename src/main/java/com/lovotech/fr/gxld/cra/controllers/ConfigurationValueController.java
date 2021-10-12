package com.lovotech.fr.gxld.cra.controllers;

import com.lovotech.fr.gxld.core.bean.cra.domain.ConfigurationValue;
import com.lovotech.fr.gxld.cra.repositories.ConfigurationValueRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/configValues")
public class ConfigurationValueController extends GenericController<ConfigurationValue>{


    public ConfigurationValueController(ConfigurationValueRepository ConfigurationValueRepository) {
        super(ConfigurationValueRepository);
    }
}
