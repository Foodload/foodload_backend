package se.foodload.application.interfaces;

import se.foodload.domain.Client;
import se.foodload.presentation.dto.ClientDTO;

public interface IClientInitService {

	/**
	 * Initiates a client in the database.
	 * 
	 * @param clientDTO The Client to register.
	 * @return
	 */
	public Client initClient(ClientDTO clientDTO);

}
