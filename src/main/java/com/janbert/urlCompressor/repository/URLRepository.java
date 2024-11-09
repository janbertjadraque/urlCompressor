package com.janbert.urlCompressor.repository;

import com.janbert.urlCompressor.model.URLEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepository extends JpaRepository<URLEntity, Long> {
}
