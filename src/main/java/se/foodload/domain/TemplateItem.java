package se.foodload.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TemplateItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "templateItemId")
    private Long id;

    private int count;

    @ManyToOne
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="templateId", nullable=false)
    @JsonBackReference
    private Template template;

    public TemplateItem(){}

    public TemplateItem(Item item, int count){
        this.item = item;
        this.count = count;
    }

    @Override
    public boolean equals(Object o){
        if(this == null)
            return  true;

        if(!(o instanceof TemplateItem))
            return false;

        return id != null && id.equals(((TemplateItem) o).getId());
    }
}
