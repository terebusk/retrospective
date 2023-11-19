package com.sis.retrospective.repository;

import com.sis.retrospective.entity.RetroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetroRepository extends JpaRepository<RetroEntity, Long> {

}

