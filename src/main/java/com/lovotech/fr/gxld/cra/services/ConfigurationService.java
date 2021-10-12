package com.lovotech.fr.gxld.cra.services;

import com.lovotech.fr.gxld.core.bean.cra.DTO.PageDTO;
import com.lovotech.fr.gxld.core.bean.cra.domain.Configuration;
import com.lovotech.fr.gxld.core.bean.cra.domain.ConfigurationValue;
import com.lovotech.fr.gxld.cra.repositories.ConfigurationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurationService extends GenericService<Configuration> {
    private final ConfigurationRepository configurationRepository;
    public ConfigurationService( ConfigurationRepository configurationRepository) {
        super(configurationRepository);
        this.configurationRepository = configurationRepository;

    }

    public PageDTO<ConfigurationValue> getAllConfigurationValueByConfigurationId(Long id , Pageable pageable){
        Optional<Configuration> configuration = configurationRepository.findById(id) ;
        Page<ConfigurationValue> pages = new PageImpl<ConfigurationValue>(configuration.get().getConfigurationValues(),pageable , configuration.get().getConfigurationValues().size());
        return new PageDTO<ConfigurationValue>(pages) ;
    }


}
