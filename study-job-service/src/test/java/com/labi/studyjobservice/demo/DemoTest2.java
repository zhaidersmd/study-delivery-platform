package com.labi.studyjobservice.demo;

import com.labi.studyjobservice.repository.StudyJobRepository;
import com.labi.studyjobservice.service.StudyJobService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
public class DemoTest2 {

    @Mock
    StudyJobRepository studyJobRepository;

    @InjectMocks
    StudyJobService studyJobService;

    @Test
    void shouldReturnPaymentWhenPaymentExists() {




    }

}
