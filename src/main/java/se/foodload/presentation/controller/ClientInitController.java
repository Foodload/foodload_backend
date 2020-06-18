package se.foodload.presentation.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import se.foodload.application.ClientInitService;
import se.foodload.application.ClientService;
import se.foodload.domain.Client;
import se.foodload.presentation.dto.ClientDTO;

@RestController
@Validated
@CrossOrigin
public class ClientInitController {
	@Autowired
	ClientInitService clientInitService;
	@Autowired
	ClientService clientService;
	
	static final String LOGIN_URL = "/login";
	
	@ResponseStatus(HttpStatus.OK)
	//@PostMapping(LOGIN_URL)
	@GetMapping(LOGIN_URL)
	public Client login(@AuthenticationPrincipal ClientDTO clientDTO) throws Exception{
	
		System.out.println(clientDTO);
		
	    Client client = clientInitService.initClient(clientDTO);
		return client;
	}
	
		
	
}
