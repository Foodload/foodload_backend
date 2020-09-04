package se.foodload.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import se.foodload.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	public Optional<Item> findByName(String name);

	public Optional<Item> findByQrCode(String qrCode);

	public Optional<List<Item>> findByNameContaining(String name);

	@Transactional
	@Query(value = "SELECT * FROM item WHERE title LIKE '%:=pattern%' LIMIT 20 OFFSET :=start :=index", nativeQuery = true)
	public Optional<List<Item>> findMatchingItems(@Param("pattern") String pattern, @Param("start") int start,
			@Param("index") int index);

	public Optional<List<Item>> findByNameStartingWith(String name);
}
