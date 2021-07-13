package io.asiam.tansiq.repositories;

import io.asiam.tansiq.models.Major;
import io.asiam.tansiq.models.Request;
import io.asiam.tansiq.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, UUID> {
    Request getByStudentIdAndMajorId(UUID studentId, UUID majorId);
    Request getByStudentIdAndRank(UUID student ,int rank);
}
