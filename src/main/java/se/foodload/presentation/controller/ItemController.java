package se.foodload.presentation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import se.foodload.application.interfaces.IClientService;
import se.foodload.application.interfaces.IItemService;
import se.foodload.domain.Client;
import se.foodload.domain.Item;
import se.foodload.domain.ItemCount;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.presentation.models.*;

@RestController
@Validated
@CrossOrigin
public class ItemController {
	@Autowired
	IItemService itemService;
	@Autowired
	IClientService clientService;

	static final String SEARCH_ITEM = "/search-item";
	static final String ADD_ITEM_QR = "/add-item";
	static final String REMOVE_ITEM_QR = "/remove-item";
	static final String ALTER_STORAGE = "/alter-storage";
	static final String INCREMENT_ITEM = "/increment-item";
	static final String DECREMENT_ITEM = "/decrement-item";
	static final String FIND_ITEM_QR = "/find-item-by-qr";
	static final String FIND_ITEM_NAME = "/find-item-by-name";
	static final String GET_ALL_ITEM_COUNTS = "/get-all-item-counts";
	static final String MOVE_ITEM_TO = "/move-item-to";
	static final String MOVE_ITEM_FROM = "/move-item-from";
	static final String DELETE_ITEM = "/delete-item";

	@PostMapping(SEARCH_ITEM)
	@ResponseStatus(HttpStatus.OK)
	public Item searchItem(@RequestBody ItemModel Item) {
		return itemService.findItem(Item.getQrCode());
	}

	@PostMapping(DECREMENT_ITEM)
	@ResponseStatus(HttpStatus.OK)
	public void decrementItem(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody IncrementModel itemCount) {
		Client client = clientService.findClient(clientDTO);
		long familyId = client.getFamily().getId();
		itemService.decrementItem(client.getFirebaseId(), itemCount.getId(), familyId);
	}

	@PostMapping(INCREMENT_ITEM)
	@ResponseStatus(HttpStatus.OK)
	public void incrementItem(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody IncrementModel itemCount) {
		Client client = clientService.findClient(clientDTO);
		long familyId = client.getFamily().getId();
		itemService.incrementItem(client.getFirebaseId(), itemCount.getId(), familyId);
	}

	@PostMapping(ADD_ITEM_QR)
	@ResponseStatus(HttpStatus.OK)
	public void addItem(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody ItemModel item) {
		Client client = clientService.findClient(clientDTO);

		if (item.getAmount() == 0) {
			item.setAmount(1);
		}
		itemService.addItem(client.getFirebaseId(), client.getFamily(), item.getQrCode(), item.getStorageType(),
				item.getAmount());

	}

	@PostMapping(REMOVE_ITEM_QR)
	@ResponseStatus(HttpStatus.OK)
	public void deleteItem(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody ItemModel item) {
		Client client = clientService.findClient(clientDTO);
		if (item.getAmount() == 0) {
			item.setAmount(1);
		}
		itemService.removeItem(client.getFirebaseId(), client.getFamily(), item.getQrCode(), item.getStorageType(),
				item.getAmount());

	}

	@GetMapping(GET_ALL_ITEM_COUNTS)
	@ResponseStatus(HttpStatus.OK)
	public List<ItemResponse> getAllItemCounts(@AuthenticationPrincipal ClientDTO clientDTO){
		Client client = clientService.findClient(clientDTO);
		List<ItemResponse> itemResponseList = new ArrayList<ItemResponse>();
		List<ItemCount> itemCounts = itemService.getAllItemCounts(client.getFamily());
		itemCounts.forEach(item -> {
			itemResponseList.add(new ItemResponse(item.getId(), item.getItem(), item.getCount(), item.getStorageType().getName()));
		});
		return itemResponseList;
	}

	// TODO alter storage.
	@PostMapping(ALTER_STORAGE)
	@ResponseStatus(HttpStatus.OK)
	public void alterStorage(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody ItemModel item) {
		Client client = clientService.findClient(clientDTO);
		itemService.alterStorage(client.getFamily(), item.getQrCode(), item.getStorageType(), item.getNewStorageType());

	}

	@PostMapping(FIND_ITEM_QR)
	@ResponseStatus(HttpStatus.OK)
	public Item findItemQr(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody ItemModel itemModel) {
		Item item = itemService.findItem(itemModel.getQrCode());
		return item;
	}

	@PostMapping(FIND_ITEM_NAME)
	@ResponseStatus(HttpStatus.OK)
	public List<Item> findItemName(@AuthenticationPrincipal ClientDTO clientDTO,
			@RequestBody ItemPatternModel itemPatternModel) {
		List<Item> items = null;
		items = itemService.findItemPattern(itemPatternModel.getName(), itemPatternModel.getStart());
		return items;
	}

	@PostMapping(MOVE_ITEM_TO)
	@ResponseStatus(HttpStatus.OK)
	public MoveItemResponse moveItemTo(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody MoveItemModel moveItemModel){
		int moveAmount = moveItemModel.getMoveAmount();
		if(moveAmount == 0){ //TODO: does this work?
			moveAmount = 1;
		}
		Client client = clientService.findClient(clientDTO);
		int newAmount = itemService.moveItemTo(client.getFamily().getId(), moveItemModel.getItemCountId(), client.getFirebaseId(),
				moveItemModel.getStorageType(), moveAmount, moveItemModel.getOldAmount());
		return new MoveItemResponse(newAmount);
	}

	@PostMapping(MOVE_ITEM_FROM)
	@ResponseStatus(HttpStatus.OK)
	public MoveItemResponse moveItemFrom(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody MoveItemModel moveItemModel){
		int moveAmount = moveItemModel.getMoveAmount();
		if(moveAmount == 0){ //TODO: does this work?
			moveAmount = 1;
		}
		Client client = clientService.findClient(clientDTO);
		int newAmount = itemService.moveItemFrom(client.getFamily().getId(), moveItemModel.getItemCountId(), client.getFirebaseId(),
				moveItemModel.getStorageType(), moveAmount, moveItemModel.getOldAmount());
		return new MoveItemResponse(newAmount);
	}

	@PostMapping(DELETE_ITEM)
	@ResponseStatus(HttpStatus.OK)
	public void deleteItem(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody DeleteItemModel deleteItemModel){
		Client client = clientService.findClient(clientDTO);

		itemService.deleteItem(client.getFamily().getId(), deleteItemModel.getItemCountId(), client.getFirebaseId(), deleteItemModel.getAmount());
	}



}
