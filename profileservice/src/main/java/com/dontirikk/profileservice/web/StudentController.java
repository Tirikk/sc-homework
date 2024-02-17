package com.dontirikk.profileservice.web;

import com.dontirikk.profileservice.service.StudentCRUDService;
import com.dontirikk.profileservice.service.StudentMapper;
import com.dontirikk.profileservice.web.dto.StudentCreationRequest;
import com.dontirikk.profileservice.web.dto.StudentDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    private final StudentCRUDService studentService;
    private final StudentMapper studentMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDTO> listStudents() {
        return studentService.listStudents()
                .stream()
                .map(studentMapper::mapToDTO)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO createStudent(@RequestBody @Valid StudentCreationRequest request) {
        log.debug("Creating student with details %s".formatted(request));
        return studentMapper.mapToDTO(studentService.createStudent(request));
    }

    @PutMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(@PathVariable UUID studentId, @RequestBody @Valid StudentDTO student) {
        log.debug("Updating student with id %s, with details %s".formatted(studentId, student));
        return studentMapper.mapToDTO(studentService.updateStudent(studentId, student));
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable UUID studentId) {
        log.info("Deleting student with id %s".formatted(studentId));
        studentService.deleteStudent(studentId);
    }
}
