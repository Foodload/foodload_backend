package se.foodload.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.foodload.application.Interfaces.IFamilyService;
import se.foodload.domain.Client;
import se.foodload.domain.Family;
import se.foodload.domain.Storage;
import se.foodload.repository.ClientRepository;
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

	
	
	
	
	
}
