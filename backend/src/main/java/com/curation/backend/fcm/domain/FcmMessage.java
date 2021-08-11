//package com.curation.backend.fcm.domain;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
//@NoArgsConstructor
//@Entity
//public class FcmMessage {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column
//    private Long toUser;
//
//    @Column
//    private Long fromUser;
//
//    @Column
//    private String message;
//
//    @Column
//    private Boolean isPush;
//
//    @Builder
//    public FcmMessage(Long toUser, Long fromUser, String message) {
//        this.toUser = toUser;
//        this.fromUser = fromUser;
//        this.message = message;
//        this.isPush = false;
//    }
//}
