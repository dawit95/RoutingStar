package com.curation.backend.route.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteStorageRepository extends JpaRepository<RouteStorage, Long> {
    Optional<RouteStorage> findByUserIdAndRouteId(Long userId, Long routeId);

    List<RouteStorage> findAllByUserId(Long id);

    Long countByRouteId(Long routeId);

    Boolean existsRouteStorageByUserIdAndRouteId(Long userId, Long routeId);
}
