package com.curation.backend.route.domain;

import com.curation.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    public List<Route> findByUserIdIn(List<Long> id);
}
