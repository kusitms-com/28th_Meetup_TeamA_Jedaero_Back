package com.backend.domain.popup.repository;

import com.backend.domain.popup.domain.Popup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PopupRepository extends JpaRepository<Popup, Long> {
    @Query("SELECT p FROM Popup p JOIN p.contract c JOIN c.university u JOIN u.user user WHERE user.id = :userId")
    Page<Popup> findPopupsPageByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT p FROM Popup p JOIN p.contract c JOIN c.university u JOIN u.user user WHERE user.id = :userId")
    List<Popup> findPopupsByUserId(@Param("userId") Long userId);
}