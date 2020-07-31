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
import se.foodload.presentation.models.ItemModel;

@RestController
@Validated
@CrossOrigin
public class ItemController {
	@Autowired
	ItemService itemService;
	@Autowired
	ClientService clientService;

	static final String SEARCH_ITEM = "/searchItem";
	static final String ADD_ITEM_QR = "/addItemQR";
	static final String REMOVE_ITEM_QR = "/removeItemQR";
	static final String ALTER_STORAGE = "/alterStorage";

	@PostMapping(SEARCH_ITEM)
	@ResponseStatus(HttpStatus.OK)
	public Item searchItem(@RequestBody ItemModel Item) {
		return itemService.findItem(Item.getQrCode());
	}

	// KANSKE EGENTLIGEN ÄR STORAGE?
	@PostMapping(ADD_ITEM_QR)
	@ResponseStatus(HttpStatus.OK)
	public void addItem(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody ItemModel item) {
		Client client = clientService.findClient(clientDTO);
		itemService.addItem(client.getFamily(), item.getQrCode(), item.getStorageType(), item.getAmmount());

	}

	@PostMapping(REMOVE_ITEM_QR)
	@ResponseStatus(HttpStatus.OK)
	public void deleteItem(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody ItemModel item) {
		Client client = clientService.findClient(clientDTO);
		System.out.println(item.getAmmount());
		itemService.deleteItem(client.getFamily(), item.getQrCode(), item.getStorageType(), item.getAmmount());

	}

	@PostMapping(ALTER_STORAGE)
	@ResponseStatus(HttpStatus.OK)
	public void alterStorage(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody ItemModel item) {
		Client client = clientService.findClient(clientDTO);
		itemService.alterStroage(client.getFamily(), item.getQrCode(), item.getStorageType(), item.getNewStorageType());

	}

}
