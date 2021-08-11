package com.curation.backend.fcm.service;

import com.curation.backend.fcm.domain.FcmMessage;
import com.curation.backend.fcm.domain.FcmMessageRepository;
import com.curation.backend.fcm.domain.FcmTokenRepository;
import com.curation.backend.fcm.dto.FcmMessageResponseDto;
import com.curation.backend.user.domain.User;
import com.curation.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class AlarmService {

    private final UserRepository userRepository;
    private final FcmMessageRepository fcmMessageRepository;
    private final FcmTokenRepository fcmTokenRepository;
    private final PushService pushService;

    public boolean checkAlarm(long userId) {
        List<FcmMessage> messageList = fcmMessageRepository.findAllByToUser(userId);
        if(messageList.isEmpty())
            return false;
        return true;
    }

    public boolean addAlarm(Long toUserId, Long fromUserId,String title,String content) {
        FcmMessage fcmMessage = FcmMessage.builder().toUser(toUserId).fromUser(fromUserId).title(title).content(content).isPush(false).build();
        User user = userRepository.findById(toUserId).orElse(null);
        if(user==null)
            return false;
        if(fcmTokenRepository.existsByUserId(toUserId)){
            //탈퇴한 user는 아니지만 현재 로그인된 브라우저가 없는 상태 message 저장
            fcmMessageRepository.save(fcmMessage);
            return false;
        }

        pushService.searchReceivedUser(toUserId,title,content);
        fcmMessage.setIsPush(true);
        fcmMessageRepository.save(fcmMessage);
        return true;
    }

    public List<FcmMessageResponseDto> getAlarms(Long userId){
        List<FcmMessageResponseDto> ansList = fcmMessageRepository.findAllByToUser(userId).stream().map(FcmMessageResponseDto::new).collect(Collectors.toList());
        for (FcmMessageResponseDto dto: ansList) {
            fcmMessageRepository.save(fcmMessageRepository.findById(dto.getId()).get().updateIsPush(true));
        }
        return ansList;
    }

}
