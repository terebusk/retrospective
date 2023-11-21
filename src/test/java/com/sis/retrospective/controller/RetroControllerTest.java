package com.sis.retrospective.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sis.retrospective.entity.RetroEntity;
import com.sis.retrospective.model.RetroRecord;
import com.sis.retrospective.repository.RetroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        givenRepositoryOfRetrospectives();

        mockMvc.perform(get("/api/retrospectives"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].date", org.hamcrest.Matchers.is("2003/01/01")))
                .andExpect(jsonPath("$.content[1].date", org.hamcrest.Matchers.is("2002/01/01")))
                .andExpect(jsonPath("$.content[2].date", org.hamcrest.Matchers.is("2001/01/01")));
    }

    @Test
    void givenRequest_whenStoringAValidRetro_thenStatus200() throws Exception {
        RetroRecord retro = new RetroRecord("Abcde", "Abc", LocalDate.of(2002, 1, 1), List.of("Greg"), null);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonBody = mapper.writeValueAsString(retro);

        mockMvc.perform(post("/api/retrospectives")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenRequest_whenStoringRetroWithNoTitle_thenStatus400() throws Exception {
        RetroRecord retro = new RetroRecord("", "Abc", LocalDate.of(2002, 1, 1), List.of("Greg"), null);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonBody = mapper.writeValueAsString(retro);

        mockMvc.perform(post("/api/retrospectives")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenPrePopulatedRetrospectivesRepository_whenGetRetrospectivesByDate_thenReturnRetrospectivesMatchingDate() throws Exception {
        givenRepositoryOfRetrospectives();

        mockMvc.perform(get("/api/retrospectives/search?byDate=2002/01/01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].date", org.hamcrest.Matchers.is("2002/01/01")));
    }

    @Test
    void givenPrePopulatedRetrospectivesRepository_whenContentTypeXml_thenReturnXmlFormattedResponse() throws Exception {
        givenRepositoryOfRetrospectives();

        mockMvc.perform(get("/api/retrospectives/search?byDate=2002/01/01")
                        .accept(MediaType.APPLICATION_XML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("PageImpl/content/content/name").string("Retrospective 2"));
    }

    private void givenRepositoryOfRetrospectives() {
        RetroEntity retroEntity = new RetroEntity();
        retroEntity.setName("Retrospective 2");
        retroEntity.setDate(LocalDate.of(2002, 1, 1));

        RetroEntity retroEntity1 = new RetroEntity();
        retroEntity1.setName("Retrospective 1");
        retroEntity1.setDate(LocalDate.of(2001, 1, 1));

        RetroEntity retroEntity2 = new RetroEntity();
        retroEntity2.setName("Retrospective 3");
        retroEntity2.setDate(LocalDate.of(2003, 1, 1));

        retroRepository.save(retroEntity);
        retroRepository.save(retroEntity1);
        retroRepository.save(retroEntity2);
    }
}