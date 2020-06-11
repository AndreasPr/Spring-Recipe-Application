package andreas.framework.springrecipeapplication.converters;

import andreas.framework.springrecipeapplication.commands.RecipeCommand;
import andreas.framework.springrecipeapplication.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryCommandToCategory;
    private final NotesCommandToNotes notesCommandToNotes;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommandToCategory, NotesCommandToNotes notesCommandToNotes,
                                 IngredientCommandToIngredient ingredientCommandToIngredient){
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.notesCommandToNotes = notesCommandToNotes;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }


    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeSource){
        if(recipeSource == null){
            return null;
        }
        final Recipe recipe = new Recipe();
        recipe.setId(recipeSource.getId());
        recipe.setDescription(recipeSource.getDescription());
        recipe.setPrepTime(recipeSource.getPrepTime());
        recipe.setCookTime(recipeSource.getCookTime());
        recipe.setServings(recipeSource.getServings());
        recipe.setSource(recipeSource.getSource());
        recipe.setUrl(recipeSource.getUrl());
        recipe.setDirections(recipeSource.getDirections());
        recipe.setDifficulty(recipeSource.getDifficulty());
        recipe.setNotes(notesCommandToNotes.convert(recipeSource.getNotes()));

        if(recipeSource.getCategories() != null && recipeSource.getCategories().size() > 0){
            recipeSource.getCategories().forEach(category -> recipe.getCategories().add(categoryCommandToCategory.convert(category)));
        }

        if(recipeSource.getIngredients() != null && recipeSource.getIngredients().size() > 0){
            recipeSource.getIngredients().forEach(ingredient -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredient)));
        }

        return recipe;
    }
}
