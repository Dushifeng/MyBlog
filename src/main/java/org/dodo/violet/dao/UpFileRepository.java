package org.dodo.violet.dao;

import org.dodo.violet.entities.UpFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UpFileRepository extends JpaRepository<UpFile,Integer>, JpaSpecificationExecutor<UpFile> {
}
