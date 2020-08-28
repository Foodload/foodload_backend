package se.foodload.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class FamilyInvite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inviteId")
	private long id;

	@ManyToOne
	@JoinColumn(name = "familyId", referencedColumnName = "familyId")
	Family familyId;

	@ManyToOne
	@JoinColumn(name = "clientId", referencedColumnName = "clientId")
	Client clientId;

	public FamilyInvite() {
	}

	public FamilyInvite(Family family, Client client) {
		this.clientId = client;
		this.familyId = family;
	}
}
