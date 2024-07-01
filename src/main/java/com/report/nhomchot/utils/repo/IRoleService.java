package com.report.nhomchot.utils.repo;

import com.report.nhomchot.entities.Role;
import org.springframework.data.jpa.repository.Query;

public interface IRoleService extends IGeneralService<Role> {
    @Query("select r from Role r where r.roleName = ?1")
    Role findByName(String roleName);
}
