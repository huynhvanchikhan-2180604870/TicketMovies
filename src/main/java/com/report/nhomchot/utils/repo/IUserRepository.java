package com.report.nhomchot.utils.repo;

import com.report.nhomchot.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    @Modifying
    @Query("Update User u set u.fullname=:fullname, u.phone=:phone WHERE u.id = :id")
    void updateInfo(@Param("id") UUID id, @Param("fullname") String fullname, @Param("phone") String phone);

    @Modifying
    @Query("Update User u set u.password=:password WHERE u.id = :id")
    void changePassword(@Param("id") UUID id, @Param("password") String password);

    @Query("select u from User u where u.username = ?1 and u.password = ?2")
    User findByUserNameAndPasswordEqual(String username, String password);

    @Query("select u from User u where u.username = ?1")
    User findByUserName(String username);

}
