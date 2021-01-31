package se.foodload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.foodload.domain.Family;
import se.foodload.domain.Template;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    Optional<Template> findByIdAndFamily(Long templateId, Family family);
}
