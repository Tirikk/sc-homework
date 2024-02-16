package com.dontirikk.profileservice.service;

import com.dontirikk.profileservice.persistence.entity.Student;
import com.dontirikk.profileservice.persistence.repository.StudentRepository;
import com.dontirikk.profileservice.web.dto.StudentCreationRequest;
import com.dontirikk.profileservice.web.dto.StudentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static com.dontirikk.profileservice.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentCRUDServiceTest {

    @Mock
    private Supplier<UUID> uuidSupplier;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentCRUDService studentCRUDService;

    @Test
    void shouldListStudents() {
        var expectedStudents = List.of(
                new Student(STUDENT_ID, STUDENT_NAME, STUDENT_EMAIL)
        );

        when(studentRepository.findAll()).thenReturn(expectedStudents);

        var actualStudents = studentCRUDService.listStudents();

        assertThat(actualStudents).isEqualTo(expectedStudents);

        verify(studentRepository).findAll();
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void shouldCreateStudent() {
        var studentCreationRequest = new StudentCreationRequest(STUDENT_NAME, STUDENT_EMAIL);
        var createdStudent = new Student(STUDENT_ID, STUDENT_NAME, STUDENT_EMAIL);

        when(uuidSupplier.get()).thenReturn(STUDENT_ID);
        when(studentRepository.save(createdStudent)).thenReturn(createdStudent);

        var savedStudent = studentCRUDService.createStudent(studentCreationRequest);

        assertThat(savedStudent).isEqualTo(createdStudent);

        verify(studentRepository).save(createdStudent);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void shouldUpdateStudent() {
        var studentToUpdate = new StudentDTO(STUDENT_ID, STUDENT_NAME, STUDENT_UPDATED_EMAIL);
        var existingStudent = new Student(STUDENT_ID, STUDENT_NAME, STUDENT_EMAIL);
        var updatedStudent = new Student(STUDENT_ID, STUDENT_NAME, STUDENT_UPDATED_EMAIL);

        when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(updatedStudent)).thenReturn(updatedStudent);

        var actualUpdatedStudent = studentCRUDService.updateStudent(STUDENT_ID, studentToUpdate);

        assertThat(actualUpdatedStudent).isEqualTo(updatedStudent);

        verify(studentRepository).findById(STUDENT_ID);
        verify(studentRepository).save(updatedStudent);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void shouldDeleteStudent() {
        studentCRUDService.deleteStudent(STUDENT_ID);

        verify(studentRepository).deleteById(STUDENT_ID);
        verifyNoMoreInteractions(studentRepository);
    }
}