package com.dontirikk.profileservice.service;

import com.dontirikk.profileservice.persistence.entity.Student;
import com.dontirikk.profileservice.web.dto.StudentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {

    StudentDTO mapToDTO(Student student);

    Student mapToEntity(StudentDTO student);
}
