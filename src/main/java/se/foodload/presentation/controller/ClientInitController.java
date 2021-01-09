package se.foodload.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import se.foodload.application.interfaces.IClientInitService;
import se.foodload.domain.Client;
import se.foodload.jwt.JwtTokenUtil;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.presentation.models.InitResponse;

@RestController
@Validated
@CrossOrigin
public class ClientInitController {
	@Autowired
	IClientInitService clientInitService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	static final String INIT = "/init";

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(INIT)
	public InitResponse init(@AuthenticationPrincipal ClientDTO clientDTO) {
		Client client = clientInitService.initClient(clientDTO);
		String token = jwtTokenUtil.createToken(client);
		//System.out.println(token);
		//System.out.println(jwtTokenUtil.exctractTokenClaim(token, Claims::getSubject));

		return  new InitResponse(client, token);
	}

}
