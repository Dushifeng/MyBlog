package org.dodo.violet.dao;

import org.dodo.violet.entities.ArticleContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArticleContentRepository extends JpaRepository<ArticleContent,Integer>, JpaSpecificationExecutor<ArticleContent> {
}
