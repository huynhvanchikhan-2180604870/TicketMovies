package com.report.nhomchot.utils.service;

import com.report.nhomchot.entities.Role;
import com.report.nhomchot.entities.User;
import com.report.nhomchot.utils.repo.IRoleRepository;
import com.report.nhomchot.utils.repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticateService {
    @Autowired
    public IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    public  void insertUser(User user){
        userRepository.save(user);
    }
    public User getUser(String userName, String password){
        return  userRepository.findByUserNameAndPasswordEqual(userName,password);
    }
    //findByUserNameEqual(String userName);
    public  User getUser(String userName){
        return userRepository.findByUserName(userName);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public List<Role> getAllRole(){
        return roleRepository.findAll();
    }

}
