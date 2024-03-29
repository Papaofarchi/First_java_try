package org.example.dao.person;

import org.example.entity.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PersonJpaRepository extends JpaRepository<Person, Long> {
    Person findByNameAndSurname(String name, String surname);
    Person findByNickname(String nickname);
}