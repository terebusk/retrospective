package com.sis.retrospective.controller;

import com.sis.retrospective.controller.util.DateFormatting;
import com.sis.retrospective.controller.util.IsValidDate;
import com.sis.retrospective.model.FeedbackRecord;
import com.sis.retrospective.model.RetroRecord;
import com.sis.retrospective.service.RetroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequestMapping("/api")
public class RetroController {

    private static final Logger log = LoggerFactory.getLogger(RetroController.class);
    private final RetroService retroService;

    public RetroController(RetroService retroService) {
        this.retroService = retroService;
    }

    @Operation(summary = "Get a paged representation of all Retrospectives")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page of existing Retrospectives, in date order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RetroRecord.class))}),
    })
    @GetMapping(value = "/retrospectives", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Page<RetroRecord> getRetrospectives(@ParameterObject @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC, value = 5) Pageable pageable) {
        log.info("Retrieving retrospectives.");
        return retroService.getAllRetrospectives(pageable);
    }


    @Operation(summary = "Search for Retrospectives by date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results page",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RetroRecord.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid date", content = @Content)
    })
    @GetMapping("/retrospectives/search")
    public Page<RetroRecord> getRetrospectivesByDate(@RequestParam @IsValidDate String byDate,
                                                     @ParameterObject @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC, value = 5) Pageable pageable) {
        log.info("Searching retrospectives by date.");
        return retroService.getRetrospectivesByDate(DateFormatting.parse(byDate), pageable);
    }


    @Operation(summary = "Create a new Retrospective")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrospective created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    })
    @PostMapping("/retrospectives")
    @ResponseStatus(HttpStatus.OK)
    public void postRetrospective(@Valid @RequestBody RetroRecord retroRecord) {
        log.info("Creating new retrospective.");
        retroService.createRetrospective(retroRecord);
    }


    @Operation(summary = "Create a new Feedback for existing Retrospective")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback added",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Long.class, description = "Id of created Feedback"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "404", description = "Invalid Retrospective name identifier, Retrospective not found", content = @Content)
    })
    @PostMapping("/retrospectives/{name}/feedbacks")
    public Long createNewFeedback(@PathVariable @NotBlank String name,
                                  @RequestBody @Valid FeedbackRecord feedbackRecord) {
        log.info("Creating new feedback.");
        return retroService.createFeedback(name, feedbackRecord);
    }


}
