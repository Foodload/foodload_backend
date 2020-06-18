package se.foodload.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.foodload.application.Interfaces.IClientInitService;
import se.foodload.domain.Client;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.repository.ClientRepository;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class ClientInitService implements IClientInitService{
	@Autowired 
	ClientRepository clientRepository;
	@Autowired
	ClientService clientService;
	
	/**
	 * Initiates a client if not found in the database already.
	 * @param clientDTO The client info init or find.
	 * @return the client.
	 */
	public Client initClient(ClientDTO clientDTO) {
		Optional<Client> foundClient = clientService.findClient(clientDTO);
		Client client = foundClient.isPresent() ? foundClient.get() : registerClient(clientDTO);
		return client;
	}
	@Override
	public Client registerClient(ClientDTO clientDTO) {
		Client newClient = new Client(clientDTO);
		return clientRepository.save(newClient);
		//Lägg till freezer/family/jadili. SENARE:
	}
}

