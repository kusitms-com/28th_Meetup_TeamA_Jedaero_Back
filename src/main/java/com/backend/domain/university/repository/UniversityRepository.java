package com.backend.domain.university.repository;

import com.backend.domain.university.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long> {
}