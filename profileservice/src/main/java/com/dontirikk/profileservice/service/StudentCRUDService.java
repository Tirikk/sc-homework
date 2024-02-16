package com.dontirikk.profileservice.service;

import com.dontirikk.profileservice.persistence.entity.Student;
import com.dontirikk.profileservice.persistence.repository.StudentRepository;
import com.dontirikk.profileservice.web.dto.StudentCreationRequest;
import com.dontirikk.profileservice.web.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentCRUDService {
    private final StudentRepository studentRepository;

    public List<Student> listStudents() {
        //TODO
        return Collections.emptyList();
    }

    public Student createStudent(StudentCreationRequest studentCreationRequest) {
        //TODO
        return null;
    }

    public Student updateStudent(StudentDTO studentDTO) {
        //TODO
        return null;
    }

    public void deleteStudent(UUID id) {
        //TODO
    }
}
