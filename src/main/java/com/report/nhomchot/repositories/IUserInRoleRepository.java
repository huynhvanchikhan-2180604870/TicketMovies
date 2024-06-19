package com.report.nhomchot.repositories;

import com.report.nhomchot.entities.Role;
import com.report.nhomchot.entities.User;
import com.report.nhomchot.entities.UserInRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface IUserInRoleRepository extends JpaRepository<UserInRole, UUID> {
//    @Query("SELECT r FROM UserInRole ur, Role r  WHERE ur.user.id = ?1 and r.id == ur.role")
//    List<Role> getAllRoleOfUser(UUID userId);
//
//    @Query("select u from UserInRole ur, User u where ur.user == ?1 and u.id == ur.id")
//    List<User> getAllUserOfRole(UUID id);
}
