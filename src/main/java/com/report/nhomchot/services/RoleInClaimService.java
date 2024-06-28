package com.report.nhomchot.services;

import com.report.nhomchot.entities.RoleInClaim;
import com.report.nhomchot.repositories.IRoleInClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleInClaimService {
    @Autowired
    private IRoleInClaimRepository roleInClaimRepository;
    public RoleInClaimService() {

    }

    public List<RoleInClaim>getAllRoleInClaim(){
        return roleInClaimRepository.findAll();
    }

//    public List<RoleInClaim> getRoleInClaimById(UUID id){
//        return roleInClaimRepository.findByRole(id);
//    }

    public RoleInClaim addRoleInClaim(RoleInClaim roleInClaim){
        return roleInClaimRepository.save(roleInClaim);
    }

//    public void deleteRoleInClaim(UUID id){
//        if (!roleInClaimRepository.existsById(id)) {
//            throw new IllegalStateException("Id with ID " + id + " does not exist.");
//        }
//        roleInClaimRepository.deleteById(id);
//    }
}
