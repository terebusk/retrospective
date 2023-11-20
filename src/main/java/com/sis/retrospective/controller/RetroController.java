package com.sis.retrospective.controller;

import com.sis.retrospective.model.FeedbackRecord;
import com.sis.retrospective.model.RetroRecord;
import com.sis.retrospective.service.RetroService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class RetroController {

    private static final Logger log = LoggerFactory.getLogger(RetroController.class);
    private final RetroService retroService;

    public RetroController(RetroService retroService) {
        this.retroService = retroService;
    }

    @GetMapping("/retrospectives")
    public Page<RetroRecord> getRetrospectives(@ParameterObject @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC, value = 5)Pageable pageable) {
        log.info("Retrieving retrospectives.");
        return retroService.getAllRetrospectives(pageable);
    }

    @PostMapping("/retrospectives")
    @ResponseStatus(HttpStatus.OK)
    public void postRetrospective(@Valid @RequestBody RetroRecord retroRecord) {
        log.info("Creating new retrospective.");
        retroService.createRetrospective(retroRecord);
    }

    @PostMapping("/retrospective/{name}/feedbacks")
    @ResponseStatus(HttpStatus.OK)
    public Long createNewFeedback(@PathVariable @NotBlank String name,
                                  @RequestBody @Valid FeedbackRecord feedbackRecord) {
        log.info("Creating new feedback.");
        return retroService.createFeedback(name, feedbackRecord);
    }


}
