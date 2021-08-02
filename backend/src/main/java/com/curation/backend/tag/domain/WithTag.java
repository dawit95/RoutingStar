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
public class WithTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @OneToMany(mappedBy = "withTag")
    private List<RouteWithTag> routeWithTags = new ArrayList<>();

    @Builder
    public WithTag(String title) {
        this.title = title;
    }
}
