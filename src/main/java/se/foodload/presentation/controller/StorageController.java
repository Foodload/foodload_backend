package se.foodload.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.foodload.application.ClientService;
import se.foodload.application.StorageService;
import se.foodload.domain.Client;
import se.foodload.domain.ItemCount;
import se.foodload.domain.Storage;
import se.foodload.presentation.dto.ClientDTO;

@RestController
@Validated
@CrossOrigin
public class StorageController {
	@Autowired
	StorageService storageService;
	@Autowired
	ClientService clientService;
	
	static final String CHECK_FRIDGE = "/checkFridge";
	static final String CHECK_FREEZER = "/checkFreezer";
	static final String CHECK_PANTRY = "/checkPantry";

	@GetMapping(CHECK_FRIDGE)
	@ResponseStatus(HttpStatus.OK)
	public ItemCount checkFridge(@AuthenticationPrincipal ClientDTO clientDTO) {
		Client client = clientService.findClient(clientDTO);
		ItemCount fridge = storageService.getFridge(client.getFamily());
		
		return fridge;

	}

	@GetMapping(CHECK_FREEZER)
	@ResponseStatus(HttpStatus.OK)
	public ItemCount checkFreezer(@AuthenticationPrincipal ClientDTO clientDTO) {
		Client client = clientService.findClient(clientDTO);
		ItemCount freezer = storageService.getFreezer(client.getFamily());
		return freezer;

	}

	@GetMapping(CHECK_PANTRY)
	@ResponseStatus(HttpStatus.OK)
	public ItemCount checkPantry(@AuthenticationPrincipal ClientDTO clientDTO) {
		Client client = clientService.findClient(clientDTO);
		ItemCount pantry = storageService.getPantry(client.getFamily());
		return pantry;

	}
}
