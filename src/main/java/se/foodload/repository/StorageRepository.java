package se.foodload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.foodload.domain.Storage;



public interface StorageRepository extends JpaRepository<Storage, Long>  {

}
