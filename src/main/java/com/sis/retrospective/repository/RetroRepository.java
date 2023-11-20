package com.sis.retrospective.repository;

import com.sis.retrospective.entity.RetroEntity;
import com.sis.retrospective.model.RetroRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetroRepository extends JpaRepository<RetroEntity, Long> {

    RetroEntity save(RetroRecord retroRecord);
}

