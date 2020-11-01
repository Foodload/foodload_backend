package se.foodload.application.interfaces;

import java.util.Optional;

import se.foodload.domain.Client;
import se.foodload.presentation.dto.ClientDTO;

public interface IClientService {

	public Optional<Client> optionalFindClient(ClientDTO clientDTO);

	public Client findClient(ClientDTO clientDTO);
}
