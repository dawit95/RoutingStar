package com.curation.backend.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

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
