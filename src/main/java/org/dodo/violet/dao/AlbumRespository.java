package org.dodo.violet.dao;

import org.dodo.violet.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlbumRespository extends JpaRepository<Album,Integer>, JpaSpecificationExecutor<Album> {
}
