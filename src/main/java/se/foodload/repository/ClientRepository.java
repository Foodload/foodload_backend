package se.foodload.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import se.foodload.domain.Client;
import se.foodload.domain.Family;

public interface ClientRepository extends JpaRepository<Client, Long> { //<Client, String>???
	
	public Optional<Client> findByFirebaseId(String firebaseId);

	public Optional<Client> findByEmail(String email);
	
	public Optional<List<Client>> findByFamily(Family family);
}


