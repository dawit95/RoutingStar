package com.curation.backend.fcm.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class FcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String token;

    @Builder
    public FcmToken(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public FcmToken update(String token){
        this.token = token;
        return this;
    }
}
