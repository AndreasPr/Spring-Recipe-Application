package andreas.framework.springrecipeapplication.converters;

import andreas.framework.springrecipeapplication.commands.UnitOfMeasureCommand;
import andreas.framework.springrecipeapplication.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final Long ID = new Long(1L);
    public static final String UNITOFMEASURE = "Unit of Measure";

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    public void setUp(){
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }
    @Test
    public void testNullParameters(){
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyObjects(){
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }
    @Test
    public void convert(){
        // Given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID);
        unitOfMeasure.setUom(UNITOFMEASURE);

        // When
        UnitOfMeasureCommand unitOfMeasureCommand = converter.convert(unitOfMeasure);

        // Then
        assertEquals(ID, unitOfMeasureCommand.getId());
        assertEquals(UNITOFMEASURE, unitOfMeasureCommand.getUom());
    }
}
