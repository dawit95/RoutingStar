package com.curation.backend.tag.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithTagRepository extends JpaRepository<WithTag, Long> {
    public List<WithTag> findByIdIn(List<Long> withTagIds);
}
