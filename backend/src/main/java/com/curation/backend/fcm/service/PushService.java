package com.curation.backend.fcm.service;

import com.curation.backend.fcm.domain.FcmToken;
import com.curation.backend.fcm.domain.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
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

    public void searchReceivedUser(Long userId,String title, String content) {
        List<FcmToken> fcmTokens = fcmTokenRepository.findFcmTokenByUserId(userId);
        // 로그인되어 있는 기기가 존재 -> 푸시 알림
        if(fcmTokens.size()!=0) {
            for (FcmToken token : fcmTokens) {
                try {
                    fcmService.sendMessageTo(token.getToken(), title, content);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}