package se.foodload.presentation.controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import se.foodload.application.ClientInitService;
import se.foodload.application.ClientService;
import se.foodload.application.FamilyService;
import se.foodload.application.StorageService;
import se.foodload.domain.Client;
import se.foodload.domain.FamilyInvite;
import se.foodload.domain.Storage;
import se.foodload.jwt.JwtTokenUtil;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.presentation.models.InitResponse;

@RestController
@Validated
@CrossOrigin
public class ClientInitController {
	@Autowired
	ClientInitService clientInitService;
	@Autowired
	ClientService clientService;
	@Autowired
	StorageService storageService;
	@Autowired
	FamilyService  familyService;
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	static final String LOGIN_URL = "/login";
	
	@ResponseStatus(HttpStatus.OK)
	//@PostMapping(LOGIN_URL)
	@GetMapping(LOGIN_URL)
	public InitResponse login(@AuthenticationPrincipal ClientDTO clientDTO) throws Exception{
		Client client = clientInitService.initClient(clientDTO);
		String token = jwtTokenUtil.createToken(client);
		System.out.println(token);
		System.out.println(jwtTokenUtil.exctractTokenClaim(token, Claims::getSubject));
		InitResponse response = new InitResponse(client, token);
		
	    return response;
	}
	
		
	
}
