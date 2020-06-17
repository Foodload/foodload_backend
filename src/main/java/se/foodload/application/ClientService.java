package se.foodload.application;

import org.springframework.beans.factory.annotation.Autowired;

import se.foodload.application.Interfaces.IClientService;
import se.foodload.domain.Client;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.repository.ClientRepository;
import java.util.Optional;
public class ClientService implements IClientService{
	@Autowired
	ClientRepository clientRepo;
	
	@Override
	public Optional<Client> findClient(ClientDTO clientDTO) {
		Optional<Client> client = clientRepo.findByFireBaseId(clientDTO.getFirebase_id());
		return client;
		
		
	}

}
