package se.foodload.presentation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import se.foodload.application.ClientService;
import se.foodload.application.FamilyService;
import se.foodload.domain.Client;
import se.foodload.presentation.dto.ClientDTO;

@RestController
@Validated
@CrossOrigin
public class FamilyController {
	@Autowired
	ClientService clientService;
	@Autowired
	FamilyService familyService;
	static final String LOGIN_URL = "/changeFamilyName";
	
	/**
	 * Enables logged in user to change family name.
	 * @param clientDTO Client that is changing his family name.
	 * @param familyName family name to change to.
	 */
	@PostMapping(LOGIN_URL)
	@ResponseStatus(HttpStatus.OK)
	public void changeFamilyName(@AuthenticationPrincipal ClientDTO clientDTO, @Valid @RequestBody String familyName) {
		Client client = clientService.findClient(clientDTO);
		familyService.changeFamilyName(client.getFamily().getId(), familyName);		
	}
	
}
