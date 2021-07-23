package com.curation.backend.tag.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteWhatTagRepository extends JpaRepository<RouteWhatTag, Long> {
}
