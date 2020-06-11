package andreas.framework.springrecipeapplication.converters;

import andreas.framework.springrecipeapplication.commands.IngredientCommand;
import andreas.framework.springrecipeapplication.commands.UnitOfMeasureCommand;
import andreas.framework.springrecipeapplication.domain.Ingredient;
import andreas.framework.springrecipeapplication.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientCommandToIngredientTest {

    public static final Long UNITOFMEASURE_ID = new Long(2L);
    public static final BigDecimal AMOUNT = new BigDecimal("2");
    public static final String DESCRIPTION = "Tomatoes";
    public static final Long ID = new Long(3L);
    public static final Recipe RECIPE = new Recipe();

    IngredientCommandToIngredient converter;

    @BeforeEach
    public void setUp(){
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }
    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new IngredientCommand()));
    }
    @Test
    public void convert(){
        // Given
        IngredientCommand command = new IngredientCommand();
        command.setId(ID);
        command.setDescription(DESCRIPTION);
        command.setAmount(AMOUNT);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UNITOFMEASURE_ID);
        command.setUom(unitOfMeasureCommand);

        // When
        Ingredient ingredient = converter.convert(command);

        // Then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UNITOFMEASURE_ID, ingredient.getUom().getId());
    }

    @Test
    public void convertWithNullUnitOfMeasure(){
        // Given
        IngredientCommand command = new IngredientCommand();
        command.setId(ID);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);

        // When
        Ingredient ingredient = converter.convert(command);

        // Then
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());

    }


}
