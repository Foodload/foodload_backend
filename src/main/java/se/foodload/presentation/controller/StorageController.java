package se.foodload.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import se.foodload.application.ClientService;
import se.foodload.application.StorageService;
import se.foodload.domain.Client;
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

	public Storage checkFridge(@AuthenticationPrincipal ClientDTO clientDTO) {
		Client client = clientService.findClient(clientDTO);
		Storage fridge = storageService.getFridge(client.getFamily());
		return fridge;
		
	}
	public Storage checkFreezer(@AuthenticationPrincipal ClientDTO clientDTO) {
		Client client = clientService.findClient(clientDTO);
		Storage freezer = storageService.getFreezer(client.getFamily());
		return freezer;
		
	}
	public Storage checkPantry(@AuthenticationPrincipal ClientDTO clientDTO) {
		Client client = clientService.findClient(clientDTO);
		Storage pantry = storageService.getPantry(client.getFamily());
		return pantry;
		
	}
}
