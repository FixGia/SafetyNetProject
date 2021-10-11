package com.project.apisafetynet.IntegrationTest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {
    class MedicalRecordControllerTestIt {

        @Autowired
        private MockMvc mockMvc;

    }
}
