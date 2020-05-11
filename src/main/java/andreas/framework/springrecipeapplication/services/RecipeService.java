package andreas.framework.springrecipeapplication.services;

import andreas.framework.springrecipeapplication.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
