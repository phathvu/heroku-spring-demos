package webmvc.repository;

import org.springframework.data.repository.CrudRepository;
import webmvc.entity.BookEntity;

import java.util.List;

public interface BookRepository extends CrudRepository<BookEntity, Integer> {
    List<BookEntity> findByNameContainingOrAuthorContaining(String name, String author);
}
