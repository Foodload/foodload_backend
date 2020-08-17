package se.foodload.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import se.foodload.application.ClientService;

import se.foodload.application.ItemService;
import se.foodload.domain.Client;
import se.foodload.domain.Item;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.presentation.models.IncrementModel;
import se.foodload.presentation.models.ItemModel;

@RestController
@Validated
@CrossOrigin
public class ItemController {
	@Autowired
	ItemService itemService;
	@Autowired
	ClientService clientService;

	static final String SEARCH_ITEM = "/search-item";
	static final String ADD_ITEM_QR = "/add-item";
	static final String REMOVE_ITEM_QR = "/remove-item";
	static final String ALTER_STORAGE = "/alter-storage";
	static final String INCREMENT_ITEM = "/increment-item";
	static final String DECREMENT_ITEM = "decrement-item";

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
	
		if(item.getAmount() ==0) {
			item.setAmount(1);
		}
		itemService.addItem(client.getFirebaseId(), client.getFamily(), item.getQrCode(), item.getStorageType(), item.getAmount());

	}

	@PostMapping(REMOVE_ITEM_QR)
	@ResponseStatus(HttpStatus.OK)
	public void deleteItem(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody ItemModel item) {
		Client client = clientService.findClient(clientDTO);
		if(item.getAmount() ==0) {
			item.setAmount(1);
		}
		itemService.deleteItem(client.getFirebaseId(),client.getFamily(), item.getQrCode(), item.getStorageType(), item.getAmount());

	}
	//TODO alter storage.
	@PostMapping(ALTER_STORAGE)
	@ResponseStatus(HttpStatus.OK)
	public void alterStorage(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody ItemModel item) {
		Client client = clientService.findClient(clientDTO);
		itemService.alterStroage(client.getFamily(), item.getQrCode(), item.getStorageType(), item.getNewStorageType());

	}

}
