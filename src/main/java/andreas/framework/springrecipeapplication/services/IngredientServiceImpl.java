package andreas.framework.springrecipeapplication.services;

import andreas.framework.springrecipeapplication.commands.IngredientCommand;
import andreas.framework.springrecipeapplication.converters.IngredientCommandToIngredient;
import andreas.framework.springrecipeapplication.converters.IngredientToIngredientCommand;
import andreas.framework.springrecipeapplication.domain.Ingredient;
import andreas.framework.springrecipeapplication.domain.Recipe;
import andreas.framework.springrecipeapplication.repositories.RecipeRepository;
import andreas.framework.springrecipeapplication.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 RecipeRepository recipeRepository){
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            log.error("recipeId Not Found");
        }
        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            log.error("ingredientId Not Found");
        }
        return ingredientCommandOptional.get();
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!recipeOptional.isPresent()){
            return new IngredientCommand();
        }
        else{
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
               Ingredient ingredientFound = ingredientOptional.get();
               ingredientFound.setDescription(ingredientCommand.getDescription());
               ingredientFound.setAmount(ingredientCommand.getAmount());
               ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
                       .orElseThrow(() -> new RuntimeException("Uom not Found!!!")));
            }
            else{

                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients()
                    .stream()
                    .filter(ingredientsOfTheRecipe -> ingredientsOfTheRecipe.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(!savedIngredientOptional.isPresent()){
                savedIngredientOptional = savedRecipe.getIngredients()
                        .stream()
                        .filter(ingredientsOfTheRecipe -> ingredientsOfTheRecipe.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(ingredientsOfTheRecipe -> ingredientsOfTheRecipe.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(ingredientsOfTheRecipe -> ingredientsOfTheRecipe.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();
            if(ingredientOptional.isPresent()){
                Ingredient ingredientForDeletion = ingredientOptional.get();
                ingredientForDeletion.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        }
    }
}
