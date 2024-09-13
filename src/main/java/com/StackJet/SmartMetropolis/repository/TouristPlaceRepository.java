package com.StackJet.SmartMetropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.StackJet.SmartMetropolis.entity.TouristPlace;

public interface TouristPlaceRepository extends JpaRepository<TouristPlace, Long> {
    // JpaRepository provides the findAll() method
}
