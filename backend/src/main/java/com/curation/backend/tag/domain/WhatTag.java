package com.curation.backend.tag.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class WhatTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @OneToMany(mappedBy = "whatTag")
    private List<RouteWhatTag> routeWhatTags = new ArrayList<>();

    @Builder
    public WhatTag(String title) {
        this.title = title;
    }
}
