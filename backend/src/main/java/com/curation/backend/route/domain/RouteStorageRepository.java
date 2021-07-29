package com.curation.backend.route.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteStorageRepository extends JpaRepository<RouteStorage, Long> {
    Optional<RouteStorage> findByUserIdAndRouteId(Long userId, Long routeId);
}
