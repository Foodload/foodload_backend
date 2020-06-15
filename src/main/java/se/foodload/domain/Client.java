package se.foodload.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import se.foodload.presentation.dto.ClientDTO;

import org.springframework.hateoas.RepresentationModel;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Client extends RepresentationModel<Client> {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "application_id")
	private Long id;
	
	private String google_id;
	
	//private String facebook_id;
	@NotNull(message = "{competence.name.missing}")
	@NotBlank(message = "{competence.name.blank}")
	private String username;
	
	@NotNull(message = "{competence.name.missing}")
	@NotBlank(message = "{competence.name.blank}")
	private String email;
	
	
	public Client(ClientDTO clientDTO) {
		this.google_id = clientDTO.getGoogle_id();
		this.username = clientDTO.getUsername();
		this.email = clientDTO.getEmail();
	}

}
