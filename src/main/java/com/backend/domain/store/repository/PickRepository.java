package com.backend.domain.store.repository;

import com.backend.domain.store.entity.Pick;
import com.backend.domain.store.entity.Store;
import com.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PickRepository extends JpaRepository<Pick, Long> {
    Optional<Pick> findByUserAndStore(User user, Store store);
    
}
