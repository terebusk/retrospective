package com.sis.retrospective.controller;

import com.sis.retrospective.model.Retro;
import com.sis.retrospective.service.RetroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RetroController {

    private final RetroService retroService;

    public RetroController(RetroService retroService) {
        this.retroService = retroService;
    }

    @GetMapping("/retrospectives")
    public List<Retro> getRetrospectives() {
        return retroService.getAllRetrospectives();
    }
}
