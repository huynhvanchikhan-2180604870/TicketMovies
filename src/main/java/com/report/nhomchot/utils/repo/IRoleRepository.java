package com.report.nhomchot.utils.repo;

import com.report.nhomchot.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r where r.name = ?1")
    Role findByName(String roleName);
    Role findRoleById(Long id);
}
