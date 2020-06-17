package se.foodload.presentation.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import se.foodload.application.ClientInitService;
import se.foodload.application.ClientService;
import se.foodload.presentation.dto.ClientDTO;

@RestController
@Validated
@CrossOrigin
public class ClientInitController {
	@Autowired
	ClientInitService clientInitService;
	ClientService clientService;
	
	static final String LOGIN_URL = "/login";
	
	
	@PostMapping(LOGIN_URL)
	public ResponseEntity<?> login(@AuthenticationPrincipal ClientDTO clientDTO) throws Exception{
		System.out.println(clientDTO);
	
		if(clientService.findClient(clientDTO).get()!=null) {
			// ISÅFALL RETURNA FREEZER OCH ALLT ANNAT TILL USER?
		}
		else {
			clientInitService.registerClient(clientDTO);
			// REGISTER FREEZER JADILI JADILI
		}
		
		return null;
		
	}
	
		
	
}
