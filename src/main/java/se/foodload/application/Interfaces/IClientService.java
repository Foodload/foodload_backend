package se.foodload.application.Interfaces;

import java.util.Optional;

import se.foodload.domain.Client;
import se.foodload.presentation.dto.ClientDTO;

public interface IClientService {

	public Optional<Client> findClient(ClientDTO clientDTO);
}
