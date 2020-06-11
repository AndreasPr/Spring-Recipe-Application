package andreas.framework.springrecipeapplication.repositories;

import andreas.framework.springrecipeapplication.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
