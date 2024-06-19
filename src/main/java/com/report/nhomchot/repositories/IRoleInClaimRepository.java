package com.report.nhomchot.repositories;

import com.report.nhomchot.entities.RoleInClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface IRoleInClaimRepository extends JpaRepository<RoleInClaim, UUID> {
    @Query("select rl from RoleInClaim rl, Role r where r.id = ?1 and rl.id = r.id")
    List<RoleInClaim> findByRole(UUID id);
}
