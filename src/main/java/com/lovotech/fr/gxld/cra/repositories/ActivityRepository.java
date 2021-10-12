package com.lovotech.fr.gxld.cra.repositories;

import java.util.List;

import com.lovotech.fr.gxld.core.bean.cra.domain.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends GenericRepository<Activity> {

  List<Activity> findByLabelContaining(String name);
  List<Activity> findTop5ByLabelContainingIgnoreCaseAndClientIdNotNull(String label);
}
