package viet.sn.regret.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import viet.sn.regret.entity.chat.ChatMessage;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,String> {
    List<ChatMessage> findByChatId(String chatId);
    Page<ChatMessage> findByChatId(String chatId, Pageable pageable);
}
