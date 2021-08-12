package com.curation.backend.fcm.service;

import com.curation.backend.fcm.domain.FcmToken;
import com.curation.backend.fcm.domain.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional
public class PushService {

    private final FcmTokenRepository fcmTokenRepository;
    private final FcmService fcmService;

    Logger logger = LoggerFactory.getLogger(PushService.class);

    public void searchReceivedUser(Long userId,String title, String content) {
        List<FcmToken> fcmTokens = fcmTokenRepository.findFcmTokenByUserId(userId);
        logger.trace("알림 유저의 토큰 갯수 {}", fcmTokens.size());
        // 로그인되어 있는 기기가 존재 -> 푸시 알림
        if(fcmTokens.size()!=0) {
            for (FcmToken token : fcmTokens) {
                try {
                    logger.trace("알림 유저의 토큰 : {}", token);
                    fcmService.sendMessageTo(token.getToken(), title, content);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}