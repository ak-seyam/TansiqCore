package io.asiam.tansiq.repositories;

import io.asiam.tansiq.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Student getByName(String name);
    Student getByEmail(String email);
}
