package se.foodload.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import se.foodload.domain.Family;
import se.foodload.domain.Storage;
import se.foodload.domain.StorageType;



public interface StorageRepository extends JpaRepository<Storage, Long>  {
	public Optional<List<Storage>> findByfamilyId(Family Family);
	public Optional<Storage> findByfamilyIdAndStorageType(Family Family, StorageType storageType);
	
}
