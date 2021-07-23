package com.curation.backend.route.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteStorageRepository extends JpaRepository<RouteStorage, Long> {
}
