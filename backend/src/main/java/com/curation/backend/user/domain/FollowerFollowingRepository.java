package com.curation.backend.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerFollowingRepository extends JpaRepository<FollowFollowing, Long> {
     List<FollowFollowing> findAllByFollower_Id(Long followerId);
}
