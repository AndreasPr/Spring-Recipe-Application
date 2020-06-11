package andreas.framework.springrecipeapplication.services;

import andreas.framework.springrecipeapplication.commands.RecipeCommand;
import andreas.framework.springrecipeapplication.converters.RecipeCommandToRecipe;
import andreas.framework.springrecipeapplication.converters.RecipeToRecipeCommand;
import andreas.framework.springrecipeapplication.domain.Recipe;
import andreas.framework.springrecipeapplication.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecipeServiceIntegrationTest {

    public static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    @Transactional
    public void testSaveOfDescription(){
        // Given
        Iterable<Recipe> recipes = recipeRepository.findAll(); // Take all entities from database
        Recipe testRecipe = recipes.iterator().next(); // Grab the first one, don't care about which one
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        // When
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        // Then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
