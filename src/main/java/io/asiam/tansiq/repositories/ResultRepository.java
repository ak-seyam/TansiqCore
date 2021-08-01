package io.asiam.tansiq.repositories;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.Result;
import io.asiam.tansiq.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, UUID> {
    @Transactional
    public void deleteByStudentIdAndMajorId(UUID studentId, UUID majorId);


}
