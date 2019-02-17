package org.dodo.violet.dao;

import org.dodo.violet.entities.ArticleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArticleInfoRepositoty extends JpaRepository<ArticleInfo,Integer>, JpaSpecificationExecutor<ArticleInfo> {
}
