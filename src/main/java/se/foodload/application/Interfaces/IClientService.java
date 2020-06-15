package se.foodload.application.Interfaces;

import se.foodload.domain.Client;
import se.foodload.presentation.dto.ClientDTO;

public interface IClientService {

	public boolean findClient(ClientDTO clientDTO);
}
