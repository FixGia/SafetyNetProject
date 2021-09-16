package com.project.apisafetynet;
import com.project.apisafetynet.Controller.PersonController;
import com.project.apisafetynet.Service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;


    @Test
    public void testGetPerson() throws Exception {
        mockMvc.perform(get("/person")).andExpect(status().isOk());
    }
    @Test
    public void testDeletePerson() throws Exception{
        mockMvc.perform(delete("/person")).andExpect(status().isOk());
    }
    @Test
    public void testUpdatePerson() throws Exception{
        mockMvc.perform(put("/person")).andExpect(status().isOk());
    }

    }

