package se.foodload.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import se.foodload.domain.Family;
import se.foodload.domain.Item;
import se.foodload.domain.ItemCount;
import se.foodload.domain.StorageType;

public interface ItemCountRepository extends JpaRepository<ItemCount, Long> {

	public Optional<List<ItemCount>> findByStorageTypeAndfamilyId(StorageType storageType, Family family);

	public Optional<List<ItemCount>> findByfamilyId(Family family);

	public Optional<ItemCount> findByStorageTypeAndItem(StorageType storageType, Item foundItem);

	/**
	 * Custom query to increment ammount on itemcount.
	 * 
	 * @param id
	 * @param familyId
	 * @return
	 */

	@Transactional
	@Query(value = "SELECT * FROM item_count WHERE itemcount_id = :id AND storage_id IN (SELECT storage_id FROM storage WHERE family_id = :familyId)", nativeQuery = true)
	public Optional<ItemCount> findByItemCountIdAndFamilyId(@Param("id") long id, @Param("familyId") long familyId);

	/**
	 * Custom query to find Itemcount in case of adding a new item, makes sure no
	 * doublets of items exists if found increment else add new itemcount.
	 * 
	 * @param qrCode
	 * @param familyId
	 * @param storageName
	 * @return
	 */

	@Transactional
	@Query(value = "SELECT * FROM item_count WHERE item_id = (SELECT item_id FROM item WHERE qr_code = :qrCode) AND storage_id = (SELECT storage_id FROM storage WHERE family_id = :familyId AND storagetype_id = (SELECT storagetype_id FROM storage_type WHERE name = :storageName))", nativeQuery = true)
	public Optional<ItemCount> findByQrcodeAndFamilyIdAndStorageType(@Param("qrCode") String qrCode,
			@Param("familyId") long familyId, @Param("storageName") String storageName);

	/**
	 * 
	 */
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO item_count (item_id, storage_id, count) SELECT item_id, storage_id, :amount as count FROM item, storage WHERE (qr_code = :qrCode) AND storagetype_id = (SELECT storagetype_id FROM storage_type WHERE name = :storageName) AND family_id = :familyId", nativeQuery = true)

	public int insertItemCount(@Param("qrCode") String qrCode, @Param("familyId") long familyId,
			@Param("storageName") String storageName, @Param("amount") int amount);
}
