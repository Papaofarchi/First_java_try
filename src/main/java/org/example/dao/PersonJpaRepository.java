package org.example.dao;

import org.example.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PersonJpaRepository extends JpaRepository<Person, Long> {
    Person findByNameAndSurname(String name, String surname);
}