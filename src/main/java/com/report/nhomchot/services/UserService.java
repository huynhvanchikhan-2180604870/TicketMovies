package com.report.nhomchot.services;

import com.report.nhomchot.entities.Ticket;
import com.report.nhomchot.entities.User;
import com.report.nhomchot.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    public UserService() {
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id){
        return userRepository.findById(id);
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user){
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalStateException("ticket with ID " +
                        user.getId() + " does not exist."));
        existingUser.setAddress(user.getAddress());
        existingUser.setAge(user.getAge());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    public void deleteUser(UUID id){
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException("User with ID " + id + " does not exist.");
        }
        userRepository.deleteById(id);
    }
}
