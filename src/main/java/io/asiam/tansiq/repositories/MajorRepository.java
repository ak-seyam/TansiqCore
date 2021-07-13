package io.asiam.tansiq.repositories;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.Student;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MajorRepository extends JpaRepository<Major, UUID> {
}
