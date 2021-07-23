package com.curation.backend.place.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceImgRepository extends JpaRepository<PlaceImg, Long> {
}
