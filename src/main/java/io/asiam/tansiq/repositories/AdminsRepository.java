package io.asiam.tansiq.repositories;

import io.asiam.tansiq.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminsRepository extends JpaRepository<Admin, UUID> {
    Admin getByEmail(String email);
}
