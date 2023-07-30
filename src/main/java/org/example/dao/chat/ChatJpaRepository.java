package org.example.dao.chat;

import org.example.entity.chat.Chat;
import org.example.entity.chat.ChatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatJpaRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c JOIN c.persons p WHERE c.type = ?1 AND p.id IN ?2 GROUP BY c HAVING COUNT(p) = ?3")
    Chat findByTypeAndPersonIds(ChatType type, List<Long> personIds, Long size);

    Chat findByTypeAndChatName(ChatType type, String chatName);
    Optional<Chat> findById(Long id);
    List<Chat> findDistinctByTypeAndPersonsNicknameOrderByTypeAsc(ChatType type, String nickname);
    List<Chat> findDistinctByType(ChatType type);
}

