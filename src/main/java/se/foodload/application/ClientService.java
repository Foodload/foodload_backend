package se.foodload.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.foodload.application.Interfaces.IClientService;
import se.foodload.domain.Client;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.repository.ClientRepository;
import java.util.Optional;


@Service
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
