package se.foodload.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import se.foodload.domain.Item;
import se.foodload.domain.ItemCount;
import se.foodload.domain.Storage;


public interface ItemCountRepository  extends JpaRepository<ItemCount, Long> {
	public Optional<List<ItemCount>> findBystorageId(Storage storage);

	public Optional<ItemCount> findBystorageIdAndItemId(Storage storage, Item foundItem);
}
