// src/main/java/fr/univrouen/rss25SB/Repository/FeedRepository.java
package fr.univrouen.rss25SB.Repository;

import fr.univrouen.rss25SB.Entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
    Optional<FeedEntity> findByTitleAndPubDate(String title, String pubDate);
}