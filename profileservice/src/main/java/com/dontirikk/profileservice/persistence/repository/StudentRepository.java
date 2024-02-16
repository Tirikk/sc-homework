package com.dontirikk.profileservice.persistence.repository;

import com.dontirikk.profileservice.persistence.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsByEmail(String email);
}
