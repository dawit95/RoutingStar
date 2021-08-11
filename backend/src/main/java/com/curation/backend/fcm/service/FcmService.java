package com.curation.backend.fcm.service;

import com.curation.backend.fcm.dto.FcmMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RequiredArgsConstructor
@Service
@Transactional
public class FcmService {

    private final String API_URL=
            "https://fcm.googleapis.com/fcm/send";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessageTo(String targetToken,String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("body", message);
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "key=AAAAcmtLg7I:APA91bHBf1B9m-wKQTc6d_RGp64r5OzFrilOEzRjry7lmtEzsTmWUVDdlI4SvNVH1KJWzwS2UF9QFmV5F88Q4ILoPXtRyTYbi83kOxTe5RumgECQkRMuVqFht157cRcBG8-pG-960dUL");

        HttpEntity<String> enti = new HttpEntity<String>(message,headers);

        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.exchange(API_URL, HttpMethod.POST, enti, String.class);
    }

    private String makeMessage(String targetToken,String title,String message) throws JsonProcessingException {
        FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
                .data(FcmMessageDto.Data.builder()
                        .title(title)
                        .message(message)
                        .build()
                )
                .to(targetToken)
                .build();

        return objectMapper.writeValueAsString(fcmMessageDto);
    }
}