package se.foodload.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
public class Family {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "familyId")
	private Long id;

	String name;

	@OneToMany(mappedBy = "family", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonIgnore
	private Set<Template> templates;

	public Family() {
	}

	public Family(String name) {
		this.name = name;
	}

	public void addTemplate(Template template){
		if(templates == null)
			templates = new HashSet<>();
		templates.add(template);
		template.setFamily(this);
	}
}
