package com.curation.backend.user.domain;

import com.curation.backend.global.config.auth.AuthProvider;
import com.sun.istack.NotNull;
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column
    private String providerId;

    @Builder
    public User(String name, String email, String profileImg, String userDescription, String fileName, Role role, AuthProvider provider, String providerId){
        this.name = name;
        this.email = email;
        this.profileImg= profileImg;
        this.userDescription = userDescription;
        this.fileName = fileName;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
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
