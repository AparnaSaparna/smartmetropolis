package com.StackJet.SmartMetropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.StackJet.SmartMetropolis.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
