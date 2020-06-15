package se.foodload.presentation.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import se.foodload.application.ClientInitService;
import se.foodload.application.ClientService;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.presentation.models.UserDetails;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

@RestController
@Validated
@CrossOrigin
public class ClientInitController {
	@Autowired
	ClientInitService clientInitService;
	ClientService clientService;
	
	static final String LOGIN_URL = "/login";
	
	
	@PostMapping(LOGIN_URL)
	public ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws Exception{
		ClientDTO clientDTO = new ClientDTO(userDetails.getUsername(), userDetails.getGoogle_id(), userDetails.getEmail());
		if(clientService.findClient(clientDTO)) {
			//USE FACEBOOK TO VALIDATE USER ID. and controll username.
		}
		else {
			clientInitService.registerClient(clientDTO);
		}
		
		return null;
		
	}
	
		
	
}
