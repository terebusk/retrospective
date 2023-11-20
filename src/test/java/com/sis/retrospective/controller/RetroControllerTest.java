package com.sis.retrospective.controller;

import com.sis.retrospective.entity.RetroEntity;
import com.sis.retrospective.repository.RetroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RetroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RetroRepository retroRepository;

    @Test
    void givenAValidGetRequest_whenRequestingListOfFeedback_thenExpectStatusOk_andExpectArray() throws Exception {
        mockMvc.perform(get("/api/retrospectives"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void givenRepositoryOfRetros_whenRequestingAPageOfResults_thenReturnRecordsOrderedByDate() throws Exception {
        RetroEntity retroEntity = new RetroEntity();
        retroEntity.setDate(LocalDate.of(2002, 1, 1));
        RetroEntity retroEntity1 = new RetroEntity();
        retroEntity1.setDate(LocalDate.of(2001, 1, 1));
        RetroEntity retroEntity2 = new RetroEntity();
        retroEntity2.setDate(LocalDate.of(2003, 1, 1));

        retroRepository.save(retroEntity);
        retroRepository.save(retroEntity1);
        retroRepository.save(retroEntity2);

        mockMvc.perform(get("/api/retrospectives"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].date", org.hamcrest.Matchers.is("2003-01-01")))
                .andExpect(jsonPath("$.content[1].date", org.hamcrest.Matchers.is("2002-01-01")))
                .andExpect(jsonPath("$.content[2].date", org.hamcrest.Matchers.is("2001-01-01")));
    }
}