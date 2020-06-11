package andreas.framework.springrecipeapplication.services;

import andreas.framework.springrecipeapplication.domain.Recipe;
import andreas.framework.springrecipeapplication.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService{

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public void saveImage(Long recipeId, MultipartFile file) {

        try{
            Recipe recipe = recipeRepository.findById(recipeId).get();

            Byte[] bytes = new Byte[file.getBytes().length];

            int j = 0;

            for (byte b : file.getBytes()){
                bytes[j++] = b;
            }
            recipe.setImage(bytes);
            recipeRepository.save(recipe);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
