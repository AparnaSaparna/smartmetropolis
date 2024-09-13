package com.StackJet.SmartMetropolis.repository;

import com.StackJet.SmartMetropolis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}