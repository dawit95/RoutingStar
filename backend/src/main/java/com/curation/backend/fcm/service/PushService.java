package com.curation.backend.fcm.service;

import com.curation.backend.fcm.domain.FcmToken;
import com.curation.backend.fcm.domain.FcmTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class PushService {

    @Autowired
    FcmTokenRepository fcmTokenRepository;

    @Autowired
    FcmService fcmService;

    public void searchReceivedUser(Long userId, String content) {
        List<FcmToken> fcmTokens = fcmTokenRepository.findFcmTokenByUserId(userId);
        // 로그인되어 있는 기기가 존재 -> 푸시 알림
        if(fcmTokens.size()!=0) {
            for (FcmToken token : fcmTokens) {
                try {
                    fcmService.sendMessageTo(token.getToken(), "알림!!!", content);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}