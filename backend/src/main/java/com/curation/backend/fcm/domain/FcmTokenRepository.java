package com.curation.backend.fcm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
//    Optional<FcmToken> findFcmTokenByUserId(Long userId);
    List<FcmToken> findFcmTokenByUserId(Long userId);
//    Optional<FcmToken> deleteAllByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
