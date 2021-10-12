package com.lovotech.fr.gxld.cra.services;

import com.lovotech.fr.gxld.core.bean.cra.domain.Client;
import com.lovotech.fr.gxld.cra.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService extends GenericService<Client>{
    public final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        super(clientRepository);
        this.clientRepository = clientRepository;
    }

    public List<Client> getByCompanyName(String keyword){
        if (keyword != "")
            return clientRepository.findTop5ByCompanyNameContainingIgnoreCase(keyword);
        else return new ArrayList<>();
    }
}
