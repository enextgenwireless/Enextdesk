package com.enextgenwireless.Enextdesk.auth.repo;

import com.enextgenwireless.Enextdesk.auth.domain.Login;
import com.enextgenwireless.Enextdesk.auth.domain.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

    @Transactional
    void deleteByLoginAndExpiryBefore(Login login, LocalDateTime now);

    PasswordResetToken findByLogin(Login login);

    PasswordResetToken findByLoginAndOtp(Login login, String otp);

}