package com.report.nhomchot.repositories;

import com.report.nhomchot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    @Query("select u from User u where u.username =?1 and u.password = ?2")
    User findByUserNameAndPasswordEqual(String userName,String password);

    @Query("select  u from User u where u.username = ?1")
    User findByUserName(String userName);
}
