package se.foodload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.foodload.domain.Family;

public interface FamilyRepository  extends JpaRepository<Family, Long> {

}
