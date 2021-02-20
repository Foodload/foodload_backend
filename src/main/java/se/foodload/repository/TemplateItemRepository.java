package se.foodload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.foodload.domain.Item;
import se.foodload.domain.Template;
import se.foodload.domain.TemplateItem;

import java.util.Optional;

public interface TemplateItemRepository extends JpaRepository<TemplateItem, Long> {
    Optional<TemplateItem> findByIdAndTemplate(Long templateItemId, Template template);
    Optional<TemplateItem> findByTemplateAndItem(Template template, Item item);
}
