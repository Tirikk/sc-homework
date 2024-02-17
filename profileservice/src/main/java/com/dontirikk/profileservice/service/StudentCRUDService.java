package com.dontirikk.profileservice.service;

import com.dontirikk.profileservice.exception.ResourceAlreadyExistsException;
import com.dontirikk.profileservice.exception.ResourceNotFoundException;
import com.dontirikk.profileservice.persistence.entity.Student;
import com.dontirikk.profileservice.persistence.repository.StudentRepository;
import com.dontirikk.profileservice.web.dto.StudentCreationRequest;
import com.dontirikk.profileservice.web.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class StudentCRUDService {
    private final Supplier<UUID> uuidSupplier;
    private final StudentRepository studentRepository;

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public Student createStudent(StudentCreationRequest studentCreationRequest) {
        if (studentRepository.existsByEmail(studentCreationRequest.email())) {
            throw new ResourceAlreadyExistsException("A student resource with email %s already exists".formatted(studentCreationRequest.email()));
        }

        var student = new Student(
                uuidSupplier.get(),
                studentCreationRequest.name(),
                studentCreationRequest.email()
        );

        return studentRepository.save(student);
    }

    public Student updateStudent(UUID id, StudentDTO studentDTO) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id %s could not be found.".formatted(id)));

        if (studentRepository.existsByEmail(studentDTO.email()) && !student.getEmail().equals(studentDTO.email())) {
            throw new ResourceAlreadyExistsException("A student resource with email %s already exists".formatted(studentDTO.email()));
        }

        student.setEmail(studentDTO.email());
        student.setName(studentDTO.name());

        return studentRepository.save(student);
    }

    public void deleteStudent(UUID id) {
        studentRepository.deleteById(id);
    }
}
