package fr.univrouen.rss25SB.Repository;

import fr.univrouen.rss25SB.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
