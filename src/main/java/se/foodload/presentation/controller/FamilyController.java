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
import se.foodload.domain.FamilyInvite;
import se.foodload.presentation.dto.ClientDTO;


@RestController
@Validated
@CrossOrigin
public class FamilyController {
	@Autowired
	ClientService clientService;
	@Autowired
	FamilyService familyService;
	static final String CHANGE_FAMILY_NAME = "/change-familyname";
	static final String INVITE_TO_FAMILY = "/invite-to-family";
	static final String CHECK_FAMILY_INVITE = "/check-familyinvite";
	static final String ACCEPT_FAMILY_INVITE = "/accept-familyinvite";
	
	/**
	 * Enables logged in user to change family name.
	 * @param clientDTO Client that is changing his family name.
	 * @param familyName family name to change to.
	 */
	@PostMapping(CHANGE_FAMILY_NAME)
	@ResponseStatus(HttpStatus.OK)
	public void changeFamilyName(@AuthenticationPrincipal ClientDTO clientDTO, @Valid @RequestBody String familyName) {
		Client client = clientService.findClient(clientDTO);
		familyService.changeFamilyName(client.getFamily().getId(), familyName);		
	}
	/**
	 * Invites a client specified by email to current clients family.
	 * @param clientDTO The client inviting 
	 * @param email of the invited family member.
	 */
	@PostMapping(INVITE_TO_FAMILY)
	@ResponseStatus(HttpStatus.OK)
	public void inviteToFamily(@AuthenticationPrincipal ClientDTO clientDTO, @Valid @RequestBody String email) {
		Client client = clientService.findClient(clientDTO);
		familyService.inviteToFamily(client.getFamily(), email);
	}
	/**
	 * Checks if current client has any family invites.
	 * @param clientDTO The client checking if he has any invites.
	 * @return
	 */
	@PostMapping(CHECK_FAMILY_INVITE)
	@ResponseStatus(HttpStatus.OK)
	public FamilyInvite checkFamilyInvite(@AuthenticationPrincipal ClientDTO clientDTO) {
		Client client = clientService.findClient(clientDTO);
		FamilyInvite familyInvite = familyService.checkFamilyInvite(client);
		return familyInvite;
	}
	/**
	 * Accepts a family invite by Id
	 * @param familyInviteId Id of invite.
	 */
	@PostMapping(ACCEPT_FAMILY_INVITE)
	@ResponseStatus(HttpStatus.OK)
	public void acceptFamilyInvite(@Valid @RequestBody long familyInviteId) {
		familyService.acceptFamilyInvite(familyInviteId);
	}
	
}