package com.StackJet.SmartMetropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.StackJet.SmartMetropolis.entity.Sport;

public interface SportRepository extends JpaRepository<Sport, Long> {
}
