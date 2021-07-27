package com.curation.backend.tag.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WhatTagRepository extends JpaRepository<WhatTag, Long> {

    public List<WhatTag> findByIdIn(List<Long> whatTagIds);

}
