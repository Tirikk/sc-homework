package com.dontirikk.profileservice.service;

import com.dontirikk.profileservice.persistence.entity.Student;
import com.dontirikk.profileservice.web.dto.StudentDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class StudentMapperTest {
    private static final UUID STUDENT_ID = UUID.randomUUID();
    private static final String STUDENT_NAME = "John Doe";
    private static final String STUDENT_EMAIL = "john.doe@email.com";

    private final StudentMapper studentMapper = new StudentMapperImpl();

    @Test
    void shouldMapToDto() {
        var student = new Student(STUDENT_ID, STUDENT_NAME, STUDENT_EMAIL);
        var expectedDto = new StudentDTO(STUDENT_ID, STUDENT_NAME, STUDENT_EMAIL);

        var actualDto = studentMapper.mapToDTO(student);

        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    void shouldMapToEntity() {
        var studentDto = new StudentDTO(STUDENT_ID, STUDENT_NAME, STUDENT_EMAIL);
        var expectedEntity = new Student(STUDENT_ID, STUDENT_NAME, STUDENT_EMAIL);

        var actualEntity = studentMapper.mapToEntity(studentDto);

        assertThat(actualEntity).isEqualTo(expectedEntity);
    }
}