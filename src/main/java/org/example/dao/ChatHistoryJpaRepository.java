package org.example.dao;

import org.example.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ChatHistoryJpaRepository extends JpaRepository<ChatHistory, Long> {
    List<ChatHistory> findByPersonNickname(String nickname);
}
