package se.foodload.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.foodload.application.Interfaces.IClientService;
import se.foodload.domain.Client;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.repository.ClientRepository;
import java.util.Optional;


@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class ClientService implements IClientService{
	@Autowired
	ClientRepository clientRepo;
	@Autowired
	ClientInitService clientInitService;
	@Override
	public Optional<Client> findClient(ClientDTO clientDTO) {
		Optional<Client> client = clientRepo.findByfirebaseId(clientDTO.getFirebaseId());
		
		return client;
		
		
	}

}
