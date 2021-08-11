package com.curation.backend.fcm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
//    List<FcmToken> findAllByUserId(Long userId);
    List<FcmToken> findFcmTokenByUserId(Long userId);
    Optional<FcmToken> deleteAllByUserId(Long userId);
}
