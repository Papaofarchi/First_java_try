package org.example.dao;

import org.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MessageJpaRepository extends JpaRepository<Message, Long> {

}
