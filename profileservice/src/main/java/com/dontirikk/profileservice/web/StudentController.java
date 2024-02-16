package com.dontirikk.profileservice.web;

import com.dontirikk.profileservice.service.StudentCRUDService;
import com.dontirikk.profileservice.service.StudentMapper;
import com.dontirikk.profileservice.web.dto.StudentCreationRequest;
import com.dontirikk.profileservice.web.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public StudentDTO createStudent(@RequestBody StudentCreationRequest request) {
        return studentMapper.mapToDTO(studentService.createStudent(request));
    }

    @PutMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(@PathVariable UUID studentId, @RequestBody StudentDTO student) {
        return studentMapper.mapToDTO(studentService.updateStudent(studentId, student));
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable UUID studentId) {
        studentService.deleteStudent(studentId);
    }

}
