package se.foodload.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.foodload.application.Interfaces.IFamilyService;
import se.foodload.application.exception.ClientNotFoundException;
import se.foodload.application.exception.FamilyInviteNotFoundException;
import se.foodload.application.exception.FamilyNotFoundException;
import se.foodload.domain.Client;
import se.foodload.domain.Family;
import se.foodload.domain.FamilyInvite;
import se.foodload.enums.ErrorEnums;
import se.foodload.redis.RedisMessagePublisher;
import se.foodload.repository.ClientRepository;
import se.foodload.repository.FamilyInviteRespository;
import se.foodload.repository.FamilyRepository;
import se.foodload.repository.StorageRepository;

@Service
public class FamilyService implements IFamilyService {
	private final String CLIENT_NOT_FOUND = ErrorEnums.CLIENTNOTFOUND.getErrorMsg();
	private final String FAMILY_INVITE_NOT_FOUND = ErrorEnums.FAMILYINVITENOTFOUND.getErrorMsg();
	private final String FAMILY_NOT_FOUND = ErrorEnums.FAMILYNOTFOUND.getErrorMsg();

	@Autowired
	ClientRepository clientRepo;
	@Autowired
	FamilyRepository familyRepo;
	@Autowired
	StorageRepository storageRepo;
	@Autowired
	FamilyInviteRespository familyInviteRepo;
	@Autowired
	RedisMessagePublisher redisMessagePublisher;

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
		if (foundFamily.isEmpty()) {
			throw new FamilyNotFoundException(FAMILY_NOT_FOUND + family_id);
		}
		Family family = foundFamily.get();
		family.setName(newFamilyName);
		familyRepo.save(family);
		return family;
	}

	@Override
	public void inviteToFamily(Family family, String email) {

		Optional<Client> client = clientRepo.findByEmail(email);
		if (client.isEmpty()) {
			throw new ClientNotFoundException(CLIENT_NOT_FOUND + email);
		}
		FamilyInvite familyInv = new FamilyInvite(family, client.get());
		familyInviteRepo.save(familyInv);
		redisMessagePublisher.publishFamilyInvite(client.get().getFirebaseId(), family.getId());
	}

	@Override
	public FamilyInvite checkFamilyInvite(Client client) {
		Optional<FamilyInvite> familyInv = familyInviteRepo.findByClientId(client);
		if (familyInv.isEmpty()) {
			throw new FamilyInviteNotFoundException(FAMILY_INVITE_NOT_FOUND + client.getEmail());

		}
		return familyInv.get();
	}

	@Override
	public void acceptFamilyInvite(long familyInviteId) {
		Optional<FamilyInvite> familyInvite = familyInviteRepo.findById(familyInviteId);
		if (familyInvite.isEmpty()) {
			throw new FamilyInviteNotFoundException(FAMILY_INVITE_NOT_FOUND + familyInviteId);
		}
		Client client = familyInvite.get().getClientId();
		Family prevFamily = client.getFamily();

		Optional<List<Client>> clients = clientRepo.findByFamilyId(prevFamily);
		if (clients.isEmpty()) {
			familyRepo.delete(prevFamily); // tar bort tidigare family, finns risk att man måste ta bort storages osv
											// för sig.
		}

		client.addFamily(familyInvite.get().getFamilyId());
		clientRepo.save(client);
		familyInviteRepo.delete(familyInvite.get());
		redisMessagePublisher.publishChangeFamily(client.getFirebaseId(), familyInviteId, prevFamily.getId());

	}

}
