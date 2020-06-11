package andreas.framework.springrecipeapplication.converters;

import andreas.framework.springrecipeapplication.commands.CategoryCommand;
import andreas.framework.springrecipeapplication.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CategoryCommandToCategoryTest {

    public static final Long ID = new Long(1L);
    public static final String DESCRIPTION = "American";

    CategoryCommandToCategory converter;

    @BeforeEach
    public void setUp(){
        converter = new CategoryCommandToCategory();
    }
    @Test
    public void testNullParameters(){
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new CategoryCommand()));
    }
    @Test
    public void convert(){
        // Given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID);
        categoryCommand.setDescription(DESCRIPTION);

        // When
        Category category = converter.convert(categoryCommand);

        //Then
        assertEquals(ID, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}
