package io.asiam.tansiq.repositories;

import io.asiam.tansiq.models.MajorInfo;
import io.asiam.tansiq.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MajorInfoRepository extends JpaRepository<MajorInfo, UUID> {
    MajorInfo getByMajorId(UUID id);
}
