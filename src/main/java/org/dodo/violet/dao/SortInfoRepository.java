package org.dodo.violet.dao;

import org.dodo.violet.entities.SortInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SortInfoRepository extends JpaRepository<SortInfo,Integer>, JpaSpecificationExecutor<SortInfo> {
}
