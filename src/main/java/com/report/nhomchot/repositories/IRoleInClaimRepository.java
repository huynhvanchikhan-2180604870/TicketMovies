package com.report.nhomchot.repositories;

import com.report.nhomchot.entities.RoleInClaim;
import com.report.nhomchot.models.RoleInClaimId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface IRoleInClaimRepository extends JpaRepository<RoleInClaim, RoleInClaimId> {

}
