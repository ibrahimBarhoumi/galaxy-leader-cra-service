package com.lovotech.fr.gxld.cra.repositories;

import com.lovotech.fr.gxld.core.bean.cra.domain.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends GenericRepository<Client> {
    List<Client> findTop5ByCompanyNameContainingIgnoreCase(String keyword);
}
