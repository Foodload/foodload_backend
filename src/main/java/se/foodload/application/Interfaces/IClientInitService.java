package se.foodload.application.Interfaces;



import se.foodload.domain.Client;
import se.foodload.presentation.dto.ClientDTO;


public interface IClientInitService {
	
	
/**
 * Registers a client in the database.	
 * @param clientDTO The Client to register.
 * @return 
 */
public Client registerClient(ClientDTO clientDTO);
	
}
