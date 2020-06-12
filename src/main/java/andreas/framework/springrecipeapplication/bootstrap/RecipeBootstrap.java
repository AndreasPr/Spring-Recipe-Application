package andreas.framework.springrecipeapplication.bootstrap;

import andreas.framework.springrecipeapplication.domain.*;
import andreas.framework.springrecipeapplication.repositories.CategoryRepository;
import andreas.framework.springrecipeapplication.repositories.RecipeRepository;
import andreas.framework.springrecipeapplication.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Profile("default")
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository){
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event){
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading Bootstrap...");
    }


    private List<Recipe> getRecipes(){

        List<Recipe> recipes = new ArrayList<>(2);

        //get Optionals
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByUom("Each");
        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByUom("Tablespoon");
        if(!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByUom("Teaspoon");
        if(!teaSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM is not found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByUom("Dash");
        if(!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM is not found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByUom("Pint");
        if(!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM is not found");
        }

        Optional<UnitOfMeasure> cupOptional = unitOfMeasureRepository.findByUom("Cup");
        if(!cupOptional.isPresent()){
            throw new RuntimeException("Expected UOM is not found");
        }

        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupUom = cupOptional.get();



        //get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        if(!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category is not found");
        }

        Optional<Category> greekCategoryOptional = categoryRepository.findByDescription("Greek");
        if(!greekCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category is not found");
        }

        Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");
        if(!italianCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category is not found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category greekCategory = greekCategoryOptional.get();
        Category italianCategory = italianCategoryOptional.get();

        //Tuna Pasta Salad Recipe
        Recipe tunaPastaRecipe = new Recipe();
        tunaPastaRecipe.setDescription("Perfect Tuna Pasta Salad");
        tunaPastaRecipe.setPrepTime(10);
        tunaPastaRecipe.setCookTime(0);
        tunaPastaRecipe.setDifficulty(Difficulty.EASY);
        tunaPastaRecipe.setDirections("Step 1" + "\n" +
                "Cook pasta according to package directions, omitting salt and fat; drain. Cool about 10 minutes." +
                "\n" +
                "Step 2" +
                "Meanwhile, stir together mayonnaise, yogurt, dill, lemon juice, water, mustard, salt, pepper, and garlic in a large bowl until thoroughly combined." +
                "\n" +
                "Step 3" +
                "Fold cooled pasta, celery, peas, and tuna into mayonnaise mixture until thoroughly coated. Serve at room temperature or chilled. Garnish with more dill.");

        Notes tunaPastaNotes = new Notes();
        tunaPastaNotes.setRecipeNotes("Calories 373 \n" +
                "Fat 19g \n" +
                "Satfat 2g \n" +
                "Unsatfat 16g \n" +
                "Protein 14g \n" +
                "Carbohydrate 36g \n" +
                "Fiber 4g \n" +
                "Sugars 3g \n" +
                "Added sugars 0g \n" +
                "Sodium 563mg \n" +
                "Calcium 3% DV \n" +
                "Potassium 4% DV \n");
//        tunaPastaNotes.setRecipe(tunaPastaRecipe);
        tunaPastaRecipe.setNotes(tunaPastaNotes);

//        tunaPastaRecipe.getIngredients().add(new Ingredient("shell pasta", new BigDecimal(8), eachUom, tunaPastaRecipe));
//        tunaPastaRecipe.getIngredients().add(new Ingredient("canola mayonnaise", new BigDecimal(.5), cupUom, tunaPastaRecipe));
//        tunaPastaRecipe.getIngredients().add(new Ingredient("cup plain whole-milk Greek yogurt", new BigDecimal(.5), cupUom, tunaPastaRecipe));
//        tunaPastaRecipe.getIngredients().add(new Ingredient("chopped fresh dill, plus more for garnish", new BigDecimal(1), tableSpoonUom, tunaPastaRecipe));

        tunaPastaRecipe.addIngredient(new Ingredient("shell pasta", new BigDecimal(8), eachUom));
        tunaPastaRecipe.addIngredient(new Ingredient("canola mayonnaise", new BigDecimal(.5), cupUom));
        tunaPastaRecipe.addIngredient(new Ingredient("cup plain whole-milk Greek yogurt", new BigDecimal(.5), cupUom));
        tunaPastaRecipe.addIngredient(new Ingredient("chopped fresh dill, plus more for garnish", new BigDecimal(1), tableSpoonUom));

        tunaPastaRecipe.setServings(4);
        tunaPastaRecipe.setUrl("http://testandreas.com");
        tunaPastaRecipe.setSource("Andreas Recipes");

        tunaPastaRecipe.getCategories().add(americanCategory);
        tunaPastaRecipe.getCategories().add(greekCategory);
        tunaPastaRecipe.getCategories().add(italianCategory);

        recipes.add(tunaPastaRecipe);

        //Deviled Eggs Recipe
        Recipe deviledEggRecipe = new Recipe();

        deviledEggRecipe.setDescription("Deviled Eggs");
        deviledEggRecipe.setPrepTime(15);
        deviledEggRecipe.setCookTime(5);
        deviledEggRecipe.setDifficulty(Difficulty.HARD);
        deviledEggRecipe.setDirections("Step 1\n" +
                "Place eggs in a large saucepan. Cover with water to 1 inch above eggs; bring just to a boil. Remove from heat; cover and let stand 15 minutes. Drain and rinse with cold running water until cool.\n" +
                "\n" +
                "Step 2\n" +
                "Peel eggs; cut in half lengthwise, and remove yolks. Place yolks in a bowl; mash with a fork. Stir in mayonnaise and next 3 ingredients (through pepper).\n" +
                "\n" +
                "Step 3\n" +
                "Spoon yolk mixture evenly into egg white halves. Sprinkle evenly with chives and paprika.");

        Notes deviledEggNotes = new Notes();
        deviledEggNotes.setRecipeNotes("Calories 95 \n" +
                "Fat 7g \n" +
                "Satfat 1.6g \n" +
                "Monofat 3.1g \n" +
                "Polyfat 1.7g \n" +
                "Protein 6.3g \n" +
                "Carbohydrate 0.6g \n" +
                "Fiber 0.0g \n" +
                "Cholesterol 186mg \n" +
                "Iron 0.9mg \n" +
                "Sodium 129mg \n" +
                "Calcium 29mg \n");
//        deviledEggNotes.setRecipe(deviledEggRecipe);
        deviledEggRecipe.setNotes(deviledEggNotes);

//        deviledEggRecipe.getIngredients().add(new Ingredient("large eggs", new BigDecimal(6), eachUom, deviledEggRecipe));
//        deviledEggRecipe.getIngredients().add(new Ingredient("canola mayonnaise", new BigDecimal(3), tableSpoonUom, deviledEggRecipe));
//        deviledEggRecipe.getIngredients().add(new Ingredient("Dijon mustard", new BigDecimal(2), teaSpoonUom, deviledEggRecipe));

        deviledEggRecipe.addIngredient(new Ingredient("large eggs", new BigDecimal(6), eachUom));
        deviledEggRecipe.addIngredient(new Ingredient("canola mayonnaise", new BigDecimal(3), tableSpoonUom));
        deviledEggRecipe.addIngredient(new Ingredient("Dijon mustard", new BigDecimal(2), teaSpoonUom));

        deviledEggRecipe.getCategories().add(americanCategory);
        deviledEggRecipe.getCategories().add(italianCategory);

        deviledEggRecipe.setSource("Andreas Recipes");
        deviledEggRecipe.setUrl("http://testandreas.com");
        deviledEggRecipe.setServings(5);

        recipes.add(deviledEggRecipe);

        return recipes;

    }//getRecipes
}// class RecipeBootstrap
