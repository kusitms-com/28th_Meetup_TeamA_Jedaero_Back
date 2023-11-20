package com.backend.domain.user.repository;

import com.backend.domain.popup.domain.Popup;
import com.backend.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findByRefreshToken(String refreshToken);

    @Query("SELECT p FROM Popup p JOIN p.contract c JOIN c.university u JOIN u.user user WHERE user.id = :userId")
    Page<Popup> findPopupsByUserId(@Param("userId") Long userId, Pageable pageable);
}