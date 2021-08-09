package com.curation.backend.place.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Transactional
    @Modifying
    @Query("delete from Place p where p.route.id = :id")
    void deleteAllByRouteId(@Param("id") Long id);

    List<Place> findByRouteId(Long id);
}
