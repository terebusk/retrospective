package com.sis.retrospective.service;

import com.sis.retrospective.entity.RetroEntity;
import com.sis.retrospective.model.RetroRecord;
import com.sis.retrospective.repository.RetroRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class RetroServiceTest {

    @Autowired
    private RetroService retroService;

    @MockBean
    private RetroRepository retroRepository;
    @Test
    void givenStoredRetros_whenGetAllRetros_thenReturnRecordsOfRetros() {
        PageRequest pageRequest = PageRequest.of(1, 10);
        List<RetroEntity> entityList = List.of(new RetroEntity());
        Page<RetroEntity> retroEntityPage = new PageImpl<>(entityList, pageRequest, entityList.size());
        given(retroRepository.findAll(Mockito.any(PageRequest.class))).willReturn(retroEntityPage);

        Page<RetroRecord> retroRecords = retroService.getAllRetrospectives(pageRequest);

        assertThat(retroRecords, contains(instanceOf(RetroRecord.class)));
    }

    @Test
    void givenStoredRetros_whenRetroHasNoFeedback_thenReturnEmptyList() {
        PageRequest pageRequest = PageRequest.of(1, 10);
        List<RetroEntity> entityList = List.of(new RetroEntity());
        Page<RetroEntity> retroEntityPage = new PageImpl<>(entityList, pageRequest, entityList.size());
        given(retroRepository.findAll(Mockito.any(PageRequest.class))).willReturn(retroEntityPage);

        Page<RetroRecord> retroRecords = retroService.getAllRetrospectives(pageRequest);

        assertThat(retroRecords.stream().findFirst().get().feedback(), is(not(nullValue())));
    }
}