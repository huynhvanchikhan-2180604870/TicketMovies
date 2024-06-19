package com.report.nhomchot.services;

import com.report.nhomchot.entities.Role;
import com.report.nhomchot.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {
    @Autowired
    private IRoleRepository roleRepository;

    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(UUID id){
        return roleRepository.findById(id);
    }

    public Role addRole(Role role){
        return roleRepository.save(role);
    }

    public void deleteRole(UUID id){
        if (!roleRepository.existsById(id)) {
            throw new IllegalStateException("Id with ID " + id + " does not exist.");
        }
        roleRepository.deleteById(id);
    }
}
