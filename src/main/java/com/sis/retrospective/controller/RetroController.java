package com.sis.retrospective.controller;

import com.sis.retrospective.model.RetroRecord;
import com.sis.retrospective.service.RetroService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RetroController {

    private final RetroService retroService;

    public RetroController(RetroService retroService) {
        this.retroService = retroService;
    }

    @GetMapping("/retrospectives")
    public Page<RetroRecord> getRetrospectives(@PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC, value = 5)Pageable pageable) {
        return retroService.getAllRetrospectives(pageable);
    }

}
