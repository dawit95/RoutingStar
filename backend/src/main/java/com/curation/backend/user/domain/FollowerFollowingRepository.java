package com.curation.backend.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerFollowingRepository extends JpaRepository<FollowFollowing, Long> {
}
