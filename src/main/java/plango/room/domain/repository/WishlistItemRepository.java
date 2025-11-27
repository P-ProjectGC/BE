package plango.room.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plango.room.domain.entity.WishlistItem;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
}
