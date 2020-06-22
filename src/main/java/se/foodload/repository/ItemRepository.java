package se.foodload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.foodload.domain.Item;


public interface ItemRepository  extends JpaRepository<Item, Long> {

}
