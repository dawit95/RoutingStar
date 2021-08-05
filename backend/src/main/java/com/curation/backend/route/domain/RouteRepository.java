package com.curation.backend.route.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    public List<Route> findByUserIdInOrderByModifiedAtDesc(List<Long> id);

    @Query("select r from Route as r left join Like as l on r.id = l.route.id group by r.id order by count(r.id) desc ")
    Collection<Route> findAllOrderByLikeCount();

    List<Route> findAllByUserId(Long id);


    List<Route> findByIdInAndUserIdNotIn(List<Long> routeIds, List<Long> userIds);
    List<Route> findByIdInAndUserIdIn(List<Long> routeIds, List<Long> userIds);
}
