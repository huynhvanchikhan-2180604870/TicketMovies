package com.report.nhomchot.services;

import com.report.nhomchot.entities.Cinema;
import com.report.nhomchot.entities.Claim;
import com.report.nhomchot.repositories.IClaimRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClaimService {
    @Autowired
    private IClaimRepository claimRepository;

    public ClaimService() {}

    public List<Claim> getClaims() {
        return claimRepository.findAll();
    }

    public Optional<Claim> getClaim(UUID id) {
        return claimRepository.findById(id);
    }

    public Claim addClaim(Claim claim) {
        return claimRepository.save(claim);
    }

    public Claim updateClaim(@NotNull Claim claim){
        Claim existingClaim = claimRepository.findById(claim.getId())
                .orElseThrow(() -> new IllegalStateException("claim with ID " +
                        claim.getId() + " does not exist."));
        existingClaim.setClaimType(claim.getClaimType());
        existingClaim.setClaimValue(claim.getClaimValue());
        existingClaim.setRoleClaims(claim.getRoleClaims());
        return claimRepository.save(existingClaim);
    }
    public void deleteClaim(UUID id){
        if (!claimRepository.existsById(id)) {
            throw new IllegalStateException("Claim with ID " + id + " does not exist.");
        }
        claimRepository.deleteById(id);
    }
}
