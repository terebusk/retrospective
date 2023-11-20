package com.sis.retrospective.controller;

import com.sis.retrospective.model.ErrorResponse;
import com.sis.retrospective.model.RetroRecord;
import com.sis.retrospective.service.RetroService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class RetroController {

    private final RetroService retroService;

    public RetroController(RetroService retroService) {
        this.retroService = retroService;
    }

    @GetMapping("/retrospectives")
    public Page<RetroRecord> getRetrospectives(@ParameterObject @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC, value = 5)Pageable pageable) {
        return retroService.getAllRetrospectives(pageable);
    }

    @PostMapping("/retrospective")
    @ResponseStatus(HttpStatus.OK)
    public void postRetrospective(@Valid @RequestBody RetroRecord retroRecord) {
        retroService.storeRetrospective(retroRecord);
    }




}
