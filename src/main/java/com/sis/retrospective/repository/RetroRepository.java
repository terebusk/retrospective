package com.sis.retrospective.repository;

import com.sis.retrospective.entity.RetroEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface RetroRepository extends JpaRepository<RetroEntity, String> {

    Page<RetroEntity> findByDate(LocalDate date, Pageable pageable);

}

