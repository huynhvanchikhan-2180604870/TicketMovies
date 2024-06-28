package com.report.nhomchot.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class RoleInClaimId implements Serializable {
    private UUID roleId;
    private UUID claimId;

    // Default constructor
    public RoleInClaimId() {}

    // Constructor with parameters
    public RoleInClaimId(UUID roleId, UUID claimId) {
        this.roleId = roleId;
        this.claimId = claimId;
    }

    // Getters and setters

    // hashCode and equals methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleInClaimId that = (RoleInClaimId) o;
        return roleId.equals(that.roleId) && claimId.equals(that.claimId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, claimId);
    }
}
