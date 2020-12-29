package se.foodload.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.foodload.application.interfaces.IClientService;

import se.foodload.application.exception.NotFoundException;
import se.foodload.domain.Client;
import se.foodload.enums.ErrorEnums;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.repository.ClientRepository;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class ClientService implements IClientService {
	@Autowired
	ClientRepository clientRepo;

	@Override
	public Optional<Client> optionalFindClient(ClientDTO clientDTO) {
		Optional<Client> client = clientRepo.findByFirebaseId(clientDTO.getFirebaseId());

		return client;

	}

	@Override
	public Client findClient(ClientDTO clientDTO) {
		Optional<Client> client = clientRepo.findByFirebaseId(clientDTO.getFirebaseId());
		if (client.isEmpty()) {
			throw new NotFoundException(ErrorEnums.CLIENT_NOT_FOUND.toString());
		}
		return client.get();
	}

}
