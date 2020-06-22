package se.foodload.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class FamilyInvite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invite_id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "family_id", referencedColumnName = "family_id")
	Family family_id;
	
	@ManyToOne
	@JoinColumn(name = "client_id", referencedColumnName = "client_id")
	Client client_id;
	
	
	public FamilyInvite() {}
}
