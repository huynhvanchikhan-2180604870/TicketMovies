package com.report.nhomchot.utils.repo;

import com.report.nhomchot.entities.ResetPasswordToken;

import javax.security.auth.login.AccountNotFoundException;

public interface IResetPasswordTokenService {
    public ResetPasswordToken generateResetPasswordToken(String email) throws AccountNotFoundException;
    public ResetPasswordToken getByToken(String token);
    public void remove(Integer id);
}
