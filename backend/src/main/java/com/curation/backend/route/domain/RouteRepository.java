package com.curation.backend.route.domain;

import com.curation.backend.user.domain.Like;
import com.curation.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    public List<Route> findByUserIdInOrderByCreatedAtDesc(List<Long> id);

    @Query("select r from Route as r left join Like as l on r.id = l.route.id group by r.id order by count(r.id) desc ")
    Collection<Route> findAllOrderByLikeCount();
}
