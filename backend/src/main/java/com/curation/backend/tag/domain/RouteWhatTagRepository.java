package com.curation.backend.tag.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface RouteWhatTagRepository extends JpaRepository<RouteWhatTag, Long> {
    @Transactional
    @Modifying
    @Query("delete from RouteWhatTag rwt where rwt.route.id = :id")
    void deleteAllByRouteId(@Param("id") Long id);

    @Query("select rwt.route.id from RouteWhatTag rwt where rwt.whatTag.id in :list group by rwt.route.id having count (rwt.id) = :size")
    List<Long> findByWhatTag(@Param("list") List<Long> whatTag, @Param("size") Long size);
}
