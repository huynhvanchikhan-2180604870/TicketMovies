package com.report.nhomchot.services;

import com.report.nhomchot.entities.Role;
import com.report.nhomchot.entities.User;
import com.report.nhomchot.entities.UserInRole;
import com.report.nhomchot.repositories.IUserInRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserInRoleService {
    @Autowired
    private IUserInRoleRepository userInRoleRepository;

    public UserInRoleService(){

    }

    public List<Role> getAllRoleOfUser(UUID user_id){
        return userInRoleRepository.getAllRoleOfUser(user_id);
    }

    public List<User> getAllUserOfRole(UUID role_id){
        return userInRoleRepository.getAllUserOfRole(role_id);
    }

    public UserInRole addUserInRole(UserInRole userInRole){
        return userInRoleRepository.save(userInRole);
    }

    public void deleteUserInRole(UUID id){
        if(!userInRoleRepository.existsById(id)){
            throw new IllegalStateException("UserInRole with ID " + id + " does not exist.");
        }
        userInRoleRepository.deleteById(id);
    }
}
