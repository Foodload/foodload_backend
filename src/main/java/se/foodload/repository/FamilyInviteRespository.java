package se.foodload.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import se.foodload.domain.Client;
import se.foodload.domain.FamilyInvite;

public interface FamilyInviteRespository  extends JpaRepository<FamilyInvite, Long> {

	Optional<FamilyInvite> findByClientId(Client client);

}
