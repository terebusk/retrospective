package com.sis.retrospective.service;

import com.sis.retrospective.entity.RetroEntity;
import com.sis.retrospective.model.RetroRecord;
import com.sis.retrospective.repository.RetroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RetroServiceTest {

    @Autowired
    private RetroService retroService;

    @MockBean
    private RetroRepository retroRepository;
    @Test
    void givenStoredRetros_whenGetAllRetros_thenReturnRecordsOfRetros() {
        when(retroRepository.findAll()).thenReturn(List.of(new RetroEntity()));

        List<RetroRecord> retroRecords = retroService.getAllRetrospectives();

        assertThat(retroRecords, contains(instanceOf(RetroRecord.class)));
    }

    @Test
    void givenStoredRetros_whenRetroHasNoFeedback_thenReturnEmptyList() {
        when(retroRepository.findAll()).thenReturn(List.of(new RetroEntity()));

        List<RetroRecord> retroRecords = retroService.getAllRetrospectives();

        assertThat(retroRecords.get(0).feedback(), is(not(nullValue())));
    }
}