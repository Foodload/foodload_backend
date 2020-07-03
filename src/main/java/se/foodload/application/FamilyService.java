package se.foodload.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.foodload.application.Interfaces.IFamilyService;
import se.foodload.domain.Client;
import se.foodload.domain.Family;
import se.foodload.domain.FamilyInvite;
import se.foodload.domain.Storage;
import se.foodload.repository.ClientRepository;
import se.foodload.repository.FamilyInviteRespository;
import se.foodload.repository.FamilyRepository;
import se.foodload.repository.StorageRepository;

@Service
public class FamilyService implements IFamilyService{
	@Autowired
	ClientRepository clientRepo;
	@Autowired
	FamilyRepository familyRepo;
	@Autowired
	StorageRepository storageRepo;
	@Autowired
	FamilyInviteRespository familyInviteRepo;
	
	@Override
	public Family createFamily(Client client, String familyName) {
		Family family = new Family(familyName);
		familyRepo.save(family);
		client.addFamily(family);
		clientRepo.save(client); 
		return family;
	}

	@Override
	public Family changeFamilyName(long family_id, String newFamilyName) {
		Optional<Family> foundFamily = familyRepo.findById(family_id);
		if(foundFamily.isEmpty()) {
			// THROW EXCEPTION.
		}
		Family family = foundFamily.get();
		family.setName(newFamilyName);
		familyRepo.save(family); // ONÖDIG?
		return family;
	}

	@Override
	public void inviteToFamily(Family family, String email) {
		Optional<Client> client = clientRepo.findByEmail(email);
		if(client.isEmpty()) {
			//throw client not found with email x.
		}
		FamilyInvite familyInv = new FamilyInvite(family, client.get()); 
		familyInviteRepo.save(familyInv);	
	}

	

	@Override
	public FamilyInvite checkFamilyInvite(Client client) {
		Optional<FamilyInvite> familyInv = familyInviteRepo.findByClientId(client);
		if(familyInv.isEmpty()) {
			//throw no invite error.
		}
		return familyInv.get();
	}

	@Override
	public void acceptFamilyInvite(long familyInviteId) {
		Optional<FamilyInvite> familyInvite =familyInviteRepo.findById(familyInviteId);
		if(familyInvite.isEmpty()) {
			//throw no invite error.
		}
		Client client = familyInvite.get().getClientId();
		Family prevFamily = client.getFamily();
		familyRepo.delete(prevFamily); //tar bort tidigare family, finns risk att man måste ta bort storages osv för sig.
		client.addFamily(familyInvite.get().getFamilyId());
		clientRepo.save(client);
		
	}

	
	
	
	
	
}
