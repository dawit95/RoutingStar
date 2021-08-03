package com.curation.backend.user.service;

import com.curation.backend.user.domain.FollowerFollowing;
import com.curation.backend.user.domain.FollowerFollowingRepository;
import com.curation.backend.user.domain.User;
import com.curation.backend.user.domain.UserRepository;
import com.curation.backend.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FollowerFollowingRepository followerFollowingRepository;

    @Transactional(readOnly = true)
    public Optional<UserResponseDto> findFollowings(Long id) {
        return userRepository.findById(id).map(UserResponseDto::new);
    }


    @Transactional
    public void follow(long followerId, long followingId) {
        Optional<User> follower = userRepository.findById(followerId);
        Optional<User> following = userRepository.findById(followingId);

        FollowerFollowing followerFollowing = FollowerFollowing.builder().follower(follower.get()).following(following.get()).build();

        followerFollowingRepository.save(followerFollowing);
    }
}
