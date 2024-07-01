package com.report.nhomchot.utils.repo;

import com.report.nhomchot.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    Optional<User> findByUsername(String username);
    void updateInfor(UUID userId,User user);
    void changePassword(UUID id, String newPass);
    void saveUserByAdmin(User user, boolean b) throws Exception;
    List<User> getPageUser(Pageable page);
    long countPage(Integer numEntities);
    public User getByResetPasswordToken(String token);
}
