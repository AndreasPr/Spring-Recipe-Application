package andreas.framework.springrecipeapplication.converters;

import andreas.framework.springrecipeapplication.commands.RecipeCommand;
import andreas.framework.springrecipeapplication.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeToRecipeCommandTest {
    public static final Long ID = new Long(1L);
    public static final String DESCRIPTION = "Hello from Description";
    public static final Integer PREPTIME = Integer.valueOf("10");
    public static final Integer COOKTIME = Integer.valueOf("30");
    public static final Integer SERVINGS = Integer.valueOf("4");
    public static final String SOURCE = "Hello from Source";
    public static final String URL = "http://www.testurl.com";
    public static final String DIRECTIONS = "Hello from Directions";
    public static final Difficulty DIFFICULTY = Difficulty.HARD;
    public static final Long CATEGORY_ID = new Long(2L);
    public static final Long INGREDIENT_ID = new Long(3L);
    public static final Long NOTES_ID = new Long(4L);

    RecipeToRecipeCommand converter;
    @BeforeEach
    public void setUp(){
        converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
                                                          new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                                                          new NotesToNotesCommand());
    }
    @Test
    public void testNullParameters(){
        assertNull(converter.convert(null));
    }
    @Test
    public void testNullObjects(){
        assertNotNull(converter.convert(new Recipe()));
    }
    @Test
    public void convert(){
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREPTIME);
        recipe.setCookTime(COOKTIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);

        Category category = new Category();
        category.setId(CATEGORY_ID);
        recipe.getCategories().add(category);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        recipe.getIngredients().add(ingredient);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        recipe.setNotes(notes);

        // When
        RecipeCommand command = converter.convert(recipe);

        // Then
        assertEquals(ID, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(PREPTIME, command.getPrepTime());
        assertEquals(COOKTIME, command.getCookTime());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(1, command.getCategories().size());
        assertEquals(1, command.getIngredients().size());
        assertEquals(NOTES_ID, command.getNotes().getId());
    }

}
