package andreas.framework.springrecipeapplication.services;

import andreas.framework.springrecipeapplication.domain.Recipe;
import andreas.framework.springrecipeapplication.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.HashSet;
import java.util.Set;


class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        //Initialize - Set Up Mock
        MockitoAnnotations.initMocks(this);
        //Create Our Service
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes() {

        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
        //How many interactions happen within the class
        verify(recipeRepository, times(1)).findAll();
    }
}
