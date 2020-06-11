package andreas.framework.springrecipeapplication.converters;

import andreas.framework.springrecipeapplication.commands.CategoryCommand;
import andreas.framework.springrecipeapplication.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryToCategoryCommandTest {

    public static final Long ID = new Long(1L);
    public static final String DESCRIPTION = "American";

    CategoryToCategoryCommand converter;

    @BeforeEach
    public void setUp(){
        converter = new CategoryToCategoryCommand();
    }
    @Test
    public void testNullParameters(){
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyObjects(){
        assertNotNull(converter.convert(new Category()));
    }
    @Test
    public void convert(){
        // Given
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        // When
        CategoryCommand categoryCommand = converter.convert(category);

        // Then
        assertEquals(ID, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }
}
