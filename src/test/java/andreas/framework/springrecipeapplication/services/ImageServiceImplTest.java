package andreas.framework.springrecipeapplication.services;

import andreas.framework.springrecipeapplication.domain.Recipe;
import andreas.framework.springrecipeapplication.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {
    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void testSaveImage() throws Exception {
        // Given
        Long Id = 1L;
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Recipe App".getBytes());
        Recipe recipe = new Recipe();
        recipe.setId(Id);
        Optional<Recipe> recipeOptional = Optional.of(recipe);


        // When
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        imageService.saveImage(Id, multipartFile);

        // Then
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe recipeSaved = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, recipeSaved.getImage().length);

    }
}
