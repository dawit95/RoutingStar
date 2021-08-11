package com.curation.backend.fcm.domain;

import com.curation.backend.global.domain.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@Entity
public class FcmMessage extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long toUser;

    @Column
    private Long fromUser;


    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Boolean isPush;

    @Builder
    public FcmMessage(Long toUser, Long fromUser, String title, String content,Boolean isPush) {
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.title = title;
        this.content = content;
        this.isPush = isPush;
    }
    public FcmMessage updateIsPush(boolean check){
        this.isPush = check;
        return this;
    }
}
