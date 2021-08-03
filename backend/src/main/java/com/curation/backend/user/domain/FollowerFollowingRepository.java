package com.curation.backend.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerFollowingRepository extends JpaRepository<FollowerFollowing, Long> {
     List<FollowerFollowing> findAllByFollower(User user);
     List<FollowerFollowing> findAllByFollowing(User user);

}
