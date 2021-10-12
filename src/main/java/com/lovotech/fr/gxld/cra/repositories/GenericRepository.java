package com.lovotech.fr.gxld.cra.repositories;

import com.lovotech.fr.gxld.core.bean.cra.domain.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * @author Admin
 */
@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity<T>> extends JpaRepository<T, Long> , JpaSpecificationExecutor<T> {
    Optional<T> findById(Long id);
}
