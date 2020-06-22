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


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Client extends RepresentationModel<Client> {
	

	@Id
	private String firebaseId;
	

	@ManyToOne
	@JoinColumn(name = "family_id", referencedColumnName = "family_id")
	Family family_id;
	
	
	public Client(ClientDTO clientDTO) {
		this.firebaseId = clientDTO.getFirebaseId();
	}
	
	public Client() {
		
	}

}
