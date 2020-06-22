package se.foodload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.foodload.domain.ItemCount;


public interface ItemCountRepository  extends JpaRepository<ItemCount, Long> {

}
