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
	public boolean findClient(ClientDTO clientDTO) {
		Optional<Client> client = clientRepo.findByGoogleID(clientDTO.getGoogle_id());
		if(client.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

}
