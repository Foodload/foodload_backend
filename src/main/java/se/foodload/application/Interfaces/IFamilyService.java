package se.foodload.application.Interfaces;

import java.util.List;

import se.foodload.domain.Client;
import se.foodload.domain.Family;
import se.foodload.domain.FamilyInvite;
import se.foodload.domain.Storage;

public interface IFamilyService {
	
	/**
	 * Creates a family for the specific user.
	 * @param client
	 * @return 
	 */
	public Family createFamily(Client client, String familyName);
	
	

	public Family changeFamilyName(long family_id, String familyName);
	
	public void inviteToFamily(Family family_id, String email);
	
	public FamilyInvite checkFamilyInvite(Client client);
	
	public void acceptFamilyInvite(long familyInviteId);
}
