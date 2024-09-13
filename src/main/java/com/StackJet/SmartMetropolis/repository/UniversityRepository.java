package com.StackJet.SmartMetropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.StackJet.SmartMetropolis.entity.University;

public interface UniversityRepository extends JpaRepository<University, Long> {
    // JpaRepository provides the findAll() method
}
