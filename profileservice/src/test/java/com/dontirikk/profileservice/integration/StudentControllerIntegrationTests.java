package com.dontirikk.profileservice.integration;

import com.dontirikk.profileservice.persistence.repository.StudentRepository;
import com.dontirikk.profileservice.web.client.AddressClient;
import com.dontirikk.profileservice.web.dto.AddressDTO;
import com.dontirikk.profileservice.web.dto.StudentCreationRequest;
import com.dontirikk.profileservice.web.dto.StudentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.dontirikk.profileservice.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
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

    @MockBean
    private AddressClient addressClient;

    @BeforeEach
    public void setUp() {
        when(addressClient.getAddress()).thenReturn(new AddressDTO(UUID.randomUUID(), STUDENT_ADDRESS));
    }

    @AfterEach
    public void cleanUp() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldCreateListAndDeleteStudent() throws Exception {
        var studentRequest = new StudentCreationRequest(STUDENT_NAME, STUDENT_EMAIL);
        var createdStudent = createStudent(studentRequest);

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
        var createdStudent = createStudent(studentRequest);

        var updateRequest = new StudentDTO(createdStudent.id(), STUDENT_NAME, STUDENT_SECOND_EMAIL);

        mockMvc.perform(put(STUDENT_RESOURCE_URL + "/" + createdStudent.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(get(STUDENT_RESOURCE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value(STUDENT_SECOND_EMAIL));
    }

    @Test
    void shouldFailCreationOnEmailConflict() throws Exception {
        var studentRequest = new StudentCreationRequest(STUDENT_NAME, STUDENT_EMAIL);
        createStudent(studentRequest);

        mockMvc.perform(post(STUDENT_RESOURCE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequest)))
                .andExpect(status().isConflict());

        mockMvc.perform(get(STUDENT_RESOURCE_URL))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldFailCreationOnValidationError() throws Exception {
        var studentRequest = new StudentCreationRequest("", "foobar");

        mockMvc.perform(post(STUDENT_RESOURCE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage", containsString("name")))
                .andExpect(jsonPath("$.errorMessage", containsString("email")));
    }

    @Test
    void shouldFailUpdateOnEmailConflict() throws Exception {
        var studentRequest = new StudentCreationRequest(STUDENT_NAME, STUDENT_EMAIL);
        createStudent(studentRequest);

        var secondStudentRequest = new StudentCreationRequest(STUDENT_NAME, STUDENT_SECOND_EMAIL);
        var secondStudent = createStudent(secondStudentRequest);

        var updateRequest = new StudentDTO(secondStudent.id(), STUDENT_NAME, STUDENT_EMAIL);

        mockMvc.perform(put(STUDENT_RESOURCE_URL + "/" + secondStudent.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldFailUpdateOnValidationError() throws Exception {
        var updateRequest = new StudentDTO(null, "", "foobar");

        mockMvc.perform(put(STUDENT_RESOURCE_URL + "/" + STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage", containsString("id")))
                .andExpect(jsonPath("$.errorMessage", containsString("name")))
                .andExpect(jsonPath("$.errorMessage", containsString("email")));
    }

    @Test
    void updateShouldBeIdempotent() throws Exception {
        var studentRequest = new StudentCreationRequest(STUDENT_NAME, STUDENT_EMAIL);
        var createdStudent = createStudent(studentRequest);

        var updateRequest = new StudentDTO(createdStudent.id(), STUDENT_NAME, STUDENT_SECOND_EMAIL);

        mockMvc.perform(put(STUDENT_RESOURCE_URL + "/" + createdStudent.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(put(STUDENT_RESOURCE_URL + "/" + createdStudent.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());
    }

    private StudentDTO createStudent(StudentCreationRequest studentRequest) throws Exception {
        var creationResult = mockMvc.perform(post(STUDENT_RESOURCE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var responseString = creationResult.getResponse().getContentAsString();
        return objectMapper.readerFor(StudentDTO.class).readValue(responseString);
    }
}
