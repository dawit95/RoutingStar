//package com.curation.backend.fcm.web;
//
//import com.curation.backend.fcm.domain.FcmTokenRepository;
//import com.curation.backend.fcm.dto.FcmTokenResponseDto;
//import com.curation.backend.fcm.dto.FcmMessageRequestDto;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("push")
//public class PushController {
//
//    private final FcmTokenRepository fcmTokenRepository;
//
//    @PostMapping("/send")
//    public String sendToToken(@RequestBody FcmMessageRequestDto requsetFcmMessageDto) throws FirebaseMessagingException {
//
//        Long fromPk = requsetFcmMessageDto.getFromPk();
//
//        List<FcmTokenResponseDto> tokenlist = fcmTokenRepository.findAllByUserId(fromPk).stream().map(FcmTokenResponseDto::new).collect(Collectors.toList());
//
//        // This registration token comes from the client FCM SDKs.
//        String registrationToken = "";
//
//        // See documentation on defining a message payload.
//        Message message = Message.builder()
//                .putData("title", "메시지를 보낼거야")
//                .putData("content", "너에게")
//                .setToken(registrationToken)
//                .build();
//
//        // Send a message to the device corresponding to the provided
//        // registration token.
//        String response = FirebaseMessaging.getInstance().send(message);
//        // Response is a message ID string.
//        System.out.println("Successfully sent message: " + response);
//
//        return response;
//    }
//}