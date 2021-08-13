package com.curation.backend.user.domain;

import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndRouteId(Long userId, Long routeId);
    List<Like> findAllByUserId(Long userId);

    Long countLikeByRouteId(Long routeId);

    Boolean existsLikeByUserIdAndRouteId(Long userId, Long routeId);

}
