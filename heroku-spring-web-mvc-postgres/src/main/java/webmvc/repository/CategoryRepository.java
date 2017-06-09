package webmvc.repository;

import org.springframework.data.repository.CrudRepository;
import webmvc.entity.CategoryEntity;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
}
