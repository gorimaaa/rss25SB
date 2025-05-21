package fr.univrouen.rss25SB.Repository;

import fr.univrouen.rss25SB.Entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
