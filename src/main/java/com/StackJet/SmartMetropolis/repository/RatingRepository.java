package com.StackJet.SmartMetropolis.repository;

import com.StackJet.SmartMetropolis.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
