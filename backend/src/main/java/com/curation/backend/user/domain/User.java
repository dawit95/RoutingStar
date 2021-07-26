package com.curation.backend.user.domain;

import com.curation.backend.global.domain.BaseTime;
import com.curation.backend.route.domain.Route;
import com.curation.backend.route.domain.RouteStorage;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SQLDelete(sql = "UPDATE user set deleted = true where id = ?")
@Where(clause = "deleted = false")
@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = true)
    private String profileImg;

    @Column(nullable = true)
    private String userDescription;

    @Column(nullable = true)
    private String fileName;

    @OneToMany(mappedBy = "user")
    List<Route> routes = new ArrayList<>();

    //내가 팔로잉 하는 사람들
    @OneToMany(mappedBy = "following")
    private List<FollowFollowing> followings = new ArrayList<>();

    //나를 팔로우하는 사람들
    @OneToMany(mappedBy = "follower")
    private List<FollowFollowing> followers = new ArrayList<>();

    @ManyToOne(targetEntity = Badge.class)
    @JoinColumn(name = "badge_id")
    private Badge badge;


    @OneToMany(mappedBy = "user")
    private List<RouteStorage> routeStorages = new ArrayList<>();

    @Column
    private boolean deleted = Boolean.FALSE;
    @Enumerated
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String profileImg, String userDescription, String fileName, Role role){
        this.name = name;
        this.email = email;
        this.profileImg= profileImg;
        this.userDescription = userDescription;
        this.fileName = fileName;
        this.role = role;
    }

    public User update(String name, String profileImg){
        this.name = name;
        this.profileImg = profileImg;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
