package se.foodload.presentation.models;

import lombok.Data;
import se.foodload.domain.Client;

@Data
public class InitResponse {

	private String token;
	private Client client;

	public InitResponse() {

	}

	public InitResponse(Client client, String token) {
		this.token = token;
		this.client = client;
	}

}
