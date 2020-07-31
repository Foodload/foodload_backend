package se.foodload.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import se.foodload.presentation.dto.ClientDTO;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value = { "firebaseId" }, allowSetters = true)
public class Client extends RepresentationModel<Client> {
	

	@Id
	@Column(name = "clientId")
	private String firebaseId;
	
	private String email;

	@ManyToOne
	@JoinColumn(name = "familyId")
	Family family;
	
	
	public Client(ClientDTO clientDTO) {
		this.firebaseId = clientDTO.getFirebaseId();
		this.email = clientDTO.getEmail();
	}
	
	public Client() {
		
	}
	// ändra så denna gör en lista med alla familjer clienten är med i.
	public void addFamily(Family family) {
		this.family = family;
		
	}
	

}
