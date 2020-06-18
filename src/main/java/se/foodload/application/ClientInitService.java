package se.foodload.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.foodload.application.Interfaces.IClientInitService;
import se.foodload.domain.Client;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.repository.ClientRepository;

@Service
public class ClientInitService implements IClientInitService{
	@Autowired 
	ClientRepository clientRepository;
	
	@Override
	public void registerClient(ClientDTO clientDTO) {
		Client newClient = new Client(clientDTO);
		clientRepository.save(newClient);
		//Lägg till freezer/family/jadili. SENARE:
	}
}

