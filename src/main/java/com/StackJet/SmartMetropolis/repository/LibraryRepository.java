package com.StackJet.SmartMetropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.StackJet.SmartMetropolis.entity.Library;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
