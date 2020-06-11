package andreas.framework.springrecipeapplication.controllers;

import andreas.framework.springrecipeapplication.commands.RecipeCommand;
import andreas.framework.springrecipeapplication.services.ImageService;
import andreas.framework.springrecipeapplication.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController controller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        controller = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ControllerExceptionHandler()).build();
    }
    @Test
    public void testImagePosting() throws Exception{
        // Given
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Recipe Application".getBytes());
        // When
        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));
        // Then
        verify(imageService, times(1)).saveImage(anyLong(), any());
    }
    @Test
    public void testShowUploadImageForm() throws Exception{
        // Given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        // When
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
        // Then
        verify(recipeService, times(1)).findCommandById(anyLong());
    }
    @Test
    public void testDisplayImage() throws Exception{
        // Given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        String textfromdb = "image here";
        Byte[] bytes = new Byte[textfromdb.getBytes().length];
        int j = 0;
        for(byte b: textfromdb.getBytes()){
            bytes[j++] = b;
        }
        recipeCommand.setImage(bytes);
        // When
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        // Then
        MockHttpServletResponse response =
                mockMvc.perform(get("/recipe/1/imageofrecipe"))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();
        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(textfromdb.getBytes().length, responseBytes.length);
    }
    @Test
    public void testGetImageNumberFormatException() throws Exception{
        mockMvc.perform(get("/recipe/bgvr/imageofrecipe"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}
