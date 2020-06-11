package andreas.framework.springrecipeapplication.services;

import andreas.framework.springrecipeapplication.commands.IngredientCommand;
import andreas.framework.springrecipeapplication.converters.IngredientCommandToIngredient;
import andreas.framework.springrecipeapplication.converters.IngredientToIngredientCommand;
import andreas.framework.springrecipeapplication.converters.UnitOfMeasureCommandToUnitOfMeasure;
import andreas.framework.springrecipeapplication.converters.UnitOfMeasureToUnitOfMeasureCommand;
import andreas.framework.springrecipeapplication.domain.Ingredient;
import andreas.framework.springrecipeapplication.domain.Recipe;
import andreas.framework.springrecipeapplication.repositories.RecipeRepository;
import andreas.framework.springrecipeapplication.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImplTest(){
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                unitOfMeasureRepository, recipeRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId(){
    }

    @Test
    public void testfindByRecipeIdAndIngredientId(){
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        // When
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        // Then
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }
    @Test
    public void testsaveIngredientCommand(){
        // Given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(5L);
        ingredientCommand.setRecipeId(4L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(5L);

        // When
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        // Then
        assertEquals(Long.valueOf(5L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
    @Test
    public void testDeleteById() throws Exception{
        // Given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();

        ingredient.setId(1L);
        recipe.addIngredient(ingredient);

        ingredient.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        // When
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        ingredientService.deleteById(3L, 1L);

        // Then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}
