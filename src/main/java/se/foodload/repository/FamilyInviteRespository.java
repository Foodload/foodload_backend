package se.foodload.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import se.foodload.domain.FamilyInvite;

public interface FamilyInviteRespository  extends JpaRepository<FamilyInvite, Long> {

}
