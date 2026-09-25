package com.solutis.helpdesk.service.user.repository;

import com.solutis.helpdesk.service.user.domain.model.User;
import com.solutis.helpdesk.service.user.domain.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    Page<User> findAllByRole(Pageable pageable, UserRole role);
}
