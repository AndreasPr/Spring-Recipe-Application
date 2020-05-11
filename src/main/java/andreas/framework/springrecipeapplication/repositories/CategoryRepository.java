package andreas.framework.springrecipeapplication.repositories;

import andreas.framework.springrecipeapplication.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);

}
