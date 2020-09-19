package se.foodload.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
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
	@Query(value = "select i from Item i where lower(i.name) like %?#{escape([0])}% escape ?#{escapeCharacter()} or lower(i.brand) like %?#{escape([0])}% escape ?#{escapeCharacter()} order by (case when lower(i.name) like ?#{escape([0])}% escape ?#{escapeCharacter()} then 1 when lower(i.name) like %?#{escape([0])} escape ?#{escapeCharacter()} then 3 else 2 end), i.name, i.brand")
	public Optional<List<Item>> findMatchingItems(String pattern, Pageable pageable);

	@Transactional
	@Query(value = "select Item where fts(pg_catalog.swedish, name, :string) AND fts(pg_catalog.swedish, brand, :string)", nativeQuery = true)
	public Optional<List<Item>> fullTextItemSearchs(@Param("string") String string);

}