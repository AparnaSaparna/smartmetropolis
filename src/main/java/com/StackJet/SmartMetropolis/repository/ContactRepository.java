package com.StackJet.SmartMetropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.StackJet.SmartMetropolis.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
