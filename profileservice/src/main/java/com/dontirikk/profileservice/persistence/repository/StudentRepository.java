package com.dontirikk.profileservice.persistence.repository;

import com.dontirikk.profileservice.persistence.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StudentRepository extends CrudRepository<Student, UUID> {
}
