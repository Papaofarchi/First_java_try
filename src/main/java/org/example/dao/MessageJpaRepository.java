package org.example.dao;

import org.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface MessageJpaRepository extends JpaRepository<Message, Long> {
    List<Message> findByPersonNickname(String nickname);
}
