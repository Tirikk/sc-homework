package com.dontirikk.profileservice.web;

import com.dontirikk.profileservice.exception.ResourceAlreadyExistsException;
import com.dontirikk.profileservice.exception.ResourceNotFoundException;
import com.dontirikk.profileservice.service.StudentCRUDService;
import com.dontirikk.profileservice.service.StudentMapper;
import com.dontirikk.profileservice.web.dto.ErrorResponse;
import com.dontirikk.profileservice.web.dto.StudentCreationRequest;
import com.dontirikk.profileservice.web.dto.StudentDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

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
        return studentMapper.mapToDTO(studentService.createStudent(request));
    }

    @PutMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(@PathVariable UUID studentId, @RequestBody @Valid StudentDTO student) {
        return studentMapper.mapToDTO(studentService.updateStudent(studentId, student));
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable UUID studentId) {
        studentService.deleteStudent(studentId);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ResourceAlreadyExistsException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        return new ResponseEntity<>(new ErrorResponse("An internal error occurred."), INTERNAL_SERVER_ERROR);
    }
}
