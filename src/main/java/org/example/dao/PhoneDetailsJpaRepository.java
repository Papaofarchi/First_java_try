package org.example.dao;

import org.example.entity.PhoneDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneDetailsJpaRepository extends JpaRepository<PhoneDetails, Long> {
}
