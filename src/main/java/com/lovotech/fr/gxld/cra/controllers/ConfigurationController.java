package com.lovotech.fr.gxld.cra.controllers;

import com.lovotech.fr.gxld.core.bean.cra.DTO.PageDTO;
import com.lovotech.fr.gxld.core.bean.cra.domain.Configuration;
import com.lovotech.fr.gxld.core.bean.cra.domain.ConfigurationValue;
import com.lovotech.fr.gxld.cra.repositories.ConfigurationRepository;
import com.lovotech.fr.gxld.cra.services.ConfigurationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/configs")
public class ConfigurationController extends GenericController<Configuration> {
    private final ConfigurationService configurationService ;
    public ConfigurationController(ConfigurationRepository configurationRepository, ConfigurationService configurationService) {

        super(configurationRepository);
        this.configurationService = configurationService;
    }
    @GetMapping("/{id}/configValue")
    public ResponseEntity<PageDTO<ConfigurationValue>> getAllConfigurationValue(@PathVariable(value = "id") Long id,
                                                                                Pageable pageable){
        return ResponseEntity.ok(configurationService.getAllConfigurationValueByConfigurationId(id , pageable));
    }

}
