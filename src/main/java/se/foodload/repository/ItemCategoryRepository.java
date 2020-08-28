package se.foodload.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import se.foodload.domain.ItemCategory;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
	public Optional<ItemCategory> findByName(String itemCategory);

}
