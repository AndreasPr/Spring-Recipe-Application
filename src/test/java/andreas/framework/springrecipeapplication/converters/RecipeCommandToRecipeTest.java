package andreas.framework.springrecipeapplication.converters;

import andreas.framework.springrecipeapplication.commands.CategoryCommand;
import andreas.framework.springrecipeapplication.commands.IngredientCommand;
import andreas.framework.springrecipeapplication.commands.NotesCommand;
import andreas.framework.springrecipeapplication.commands.RecipeCommand;
import andreas.framework.springrecipeapplication.domain.Difficulty;
import andreas.framework.springrecipeapplication.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeCommandToRecipeTest {

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "Hello from Recipe";
    public static final Integer PREPTIME = Integer.valueOf(20);
    public static final Integer COOKTIME = Integer.valueOf(50);
    public static final Integer SERVINGS = Integer.valueOf(4);
    public static final String SOURCE = "Our Source";
    public static final String URL = "http://www.testsource.com";
    public static final String DIRECTIONS = "Hello from Directions";
    public static final Difficulty DIFFICULTY = Difficulty.HARD;
    public static final Long INGREDIENT_ID = 2L;
    public static final Long NOTES_ID = 3L;
    public static final Long CATEGORY_ID = 4L;

    RecipeCommandToRecipe converter;

    @BeforeEach
    public void setUp(){
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                                              new NotesCommandToNotes(),
                                              new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));
    }
    @Test
    public void testNullParameters(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert(){
        // Given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setPrepTime(PREPTIME);
        recipeCommand.setCookTime(COOKTIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setDifficulty(DIFFICULTY);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CATEGORY_ID);
        recipeCommand.getCategories().add(categoryCommand1);

        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId(INGREDIENT_ID);
        recipeCommand.getIngredients().add(ingredientCommand1);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);
        recipeCommand.setNotes(notesCommand);

        // Then
        Recipe recipe = converter.convert(recipeCommand);
        assertNotNull(recipe);
        assertEquals(ID, recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(PREPTIME, recipe.getPrepTime());
        assertEquals(COOKTIME, recipe.getCookTime());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(1, recipe.getCategories().size());
        assertEquals(1, recipe.getIngredients().size());
    }

}
