package com.curation.backend.tag.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RouteWithTagRepository extends JpaRepository<RouteWithTag, Long> {
    @Transactional
    @Modifying
    @Query("delete from RouteWithTag rwt where rwt.route.id = :id")
    void deleteAllByRouteId(@Param("id") Long id);
}
