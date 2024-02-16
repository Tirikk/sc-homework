package com.dontirikk.profileservice.persistence.repository;

import com.dontirikk.profileservice.persistence.entity.Student;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface StudentRepository extends ListCrudRepository<Student, UUID> {
}
