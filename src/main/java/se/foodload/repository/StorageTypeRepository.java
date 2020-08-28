package se.foodload.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import se.foodload.domain.StorageType;

public interface StorageTypeRepository extends JpaRepository<StorageType, Long> {
	public Optional<StorageType> findByName(String storageType);
}
