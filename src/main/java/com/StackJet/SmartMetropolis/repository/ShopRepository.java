package com.StackJet.SmartMetropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.StackJet.SmartMetropolis.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
