package se.foodload.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import se.foodload.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
	public Optional<Client> findByfirebaseId(String firebaseId);
	
}


