package com.dontirikk.profileservice.integration;

import com.dontirikk.profileservice.persistence.repository.StudentRepository;
import com.dontirikk.profileservice.web.dto.StudentCreationRequest;
import com.dontirikk.profileservice.web.dto.StudentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.dontirikk.profileservice.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerIntegrationTests {
    public static final String STUDENT_RESOURCE_URL = "/api/v1/student";

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void cleanUp() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldCreateListAndDeleteStudent() throws Exception {
        var studentRequest = new StudentCreationRequest(STUDENT_NAME, STUDENT_EMAIL);

        var creationResult = mockMvc.perform(post(STUDENT_RESOURCE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var responseString = creationResult.getResponse().getContentAsString();
        var createdStudent = (StudentDTO) objectMapper.readerFor(StudentDTO.class).readValue(responseString);

        assertThat(createdStudent.name()).isEqualTo(STUDENT_NAME);
        assertThat(createdStudent.email()).isEqualTo(STUDENT_EMAIL);
        assertThat(createdStudent.id()).isNotNull();

        mockMvc.perform(get(STUDENT_RESOURCE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(STUDENT_NAME))
                .andExpect(jsonPath("$[0].email").value(STUDENT_EMAIL))
                .andExpect(jsonPath("$[0].id").value(createdStudent.id().toString()));

        mockMvc.perform(delete(STUDENT_RESOURCE_URL + "/" + createdStudent.id()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(STUDENT_RESOURCE_URL))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldCreateThenUpdateStudent() throws Exception {
        var studentRequest = new StudentCreationRequest(STUDENT_NAME, STUDENT_EMAIL);

        var creationResult = mockMvc.perform(post(STUDENT_RESOURCE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var responseString = creationResult.getResponse().getContentAsString();
        var createdStudent = (StudentDTO) objectMapper.readerFor(StudentDTO.class).readValue(responseString);

        var updateRequest = new StudentDTO(createdStudent.id(), STUDENT_NAME, STUDENT_UPDATED_EMAIL);

        mockMvc.perform(put(STUDENT_RESOURCE_URL + "/" + createdStudent.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(get(STUDENT_RESOURCE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value(STUDENT_UPDATED_EMAIL));
    }
}