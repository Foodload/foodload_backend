package se.foodload.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "templateId")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<TemplateItem> templateItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familyId", nullable = false)
    @JsonBackReference
    private Family family;

    public Template(){}

    public Template(String name){
        this.name = name;
        this.templateItems = new HashSet<>();
    }

    public void addTemplateItem(TemplateItem templateItem){
        if(templateItems == null)
            templateItems = new HashSet<>();
        templateItems.add(templateItem);
        templateItem.setTemplate(this);
    }

    public void removeTemplateItem(TemplateItem templateItem){
        if(templateItems == null)
            return;
        templateItems.remove(templateItem);
        templateItem.setTemplate(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Template))
            return false;

        return id != null && id.equals(((Template) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
