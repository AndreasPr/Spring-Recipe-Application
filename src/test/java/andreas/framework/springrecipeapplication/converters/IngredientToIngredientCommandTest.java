package andreas.framework.springrecipeapplication.converters;

import andreas.framework.springrecipeapplication.commands.IngredientCommand;
import andreas.framework.springrecipeapplication.domain.Ingredient;
import andreas.framework.springrecipeapplication.domain.Recipe;
import andreas.framework.springrecipeapplication.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientToIngredientCommandTest {

    public static final Long ID = new Long(1L);
    public static final String DESCRIPTION = "Potatoes";
    public static final BigDecimal AMOUNT = new BigDecimal("5");
    public static final Long UnitOfMeasure_ID = new Long(2L);
    public static final Recipe RECIPE = new Recipe();

    IngredientToIngredientCommand converter;

    @BeforeEach
    public void setUp(){
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }
    @Test
    public void testNullParameters(){
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new Ingredient()));
    }
    @Test
    public void convert(){
        // Given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setRecipe(RECIPE);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UnitOfMeasure_ID);
        ingredient.setUom(uom);

        // When
        IngredientCommand ingredientCommand = converter.convert(ingredient);

        // Then
        assertEquals(ID, ingredientCommand.getId());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertNotNull(ingredientCommand.getUom());
        assertEquals(UnitOfMeasure_ID, ingredientCommand.getUom().getId());
    }
}
