package org.example.dao.person;

import org.example.entity.person.PhoneDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneDetailsJpaRepository extends JpaRepository<PhoneDetails, Long> {
}
