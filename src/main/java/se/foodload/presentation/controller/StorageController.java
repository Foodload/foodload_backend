package se.foodload.presentation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.foodload.application.ClientService;
import se.foodload.application.StorageService;
import se.foodload.domain.Client;
import se.foodload.domain.ItemCount;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.presentation.models.ItemResponse;

@RestController
@Validated
@CrossOrigin
public class StorageController {
	@Autowired
	StorageService storageService;
	@Autowired
	ClientService clientService;
	
	static final String CHECK_FRIDGE = "/check-fridge";
	static final String CHECK_FREEZER = "/check-freezer";
	static final String CHECK_PANTRY = "/check-pantry";

	@GetMapping(CHECK_FRIDGE)
	@ResponseStatus(HttpStatus.OK)
	public List<ItemResponse> checkFridge(@AuthenticationPrincipal ClientDTO clientDTO) {
		Client client = clientService.findClient(clientDTO);
		List<ItemResponse> itemList = new ArrayList<ItemResponse>();
		List<ItemCount> fridge = storageService.getFridge(client.getFamily());
		fridge.forEach(item->{
			itemList.add(new ItemResponse(item.getItem(), item.getCount()));
		});
		System.out.println(itemList);
		return itemList;

	}

	@GetMapping(CHECK_FREEZER)
	@ResponseStatus(HttpStatus.OK)
	public List<ItemCount> checkFreezer(@AuthenticationPrincipal ClientDTO clientDTO) {
		Client client = clientService.findClient(clientDTO);
		List<ItemResponse> itemList = new ArrayList<ItemResponse>();
		List<ItemCount> freezer = storageService.getFreezer(client.getFamily());
		freezer.forEach(item->{
			itemList.add(new ItemResponse(item.getItem(), item.getCount()));
		});
		return freezer;

	}

	@GetMapping(CHECK_PANTRY)
	@ResponseStatus(HttpStatus.OK)
	public List<ItemCount> checkPantry(@AuthenticationPrincipal ClientDTO clientDTO) {
		Client client = clientService.findClient(clientDTO);
		List<ItemResponse> itemList = new ArrayList<ItemResponse>();
		List<ItemCount> pantry = storageService.getPantry(client.getFamily());
		pantry.forEach(item->{
			itemList.add(new ItemResponse(item.getItem(), item.getCount()));
		});
		return pantry;

	}
}
