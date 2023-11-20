package com.backend.domain.popup.repository;

import com.backend.domain.popup.domain.Popup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopupRepository extends JpaRepository<Popup, Long> {
}