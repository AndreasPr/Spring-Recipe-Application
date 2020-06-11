package andreas.framework.springrecipeapplication.converters;

import andreas.framework.springrecipeapplication.commands.UnitOfMeasureCommand;
import andreas.framework.springrecipeapplication.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final Long LONG_VALUE = new Long(2L);
    public static final String UOM = "UnitOfMeasure";

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    public void setUp(){
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullParameters(){
        // I am expecting to return null
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        // I am expecting to return back as least an empty object and not a NullPointerException
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert(){
        //given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(LONG_VALUE);
        command.setUom(UOM);

        //when
        UnitOfMeasure unitOfMeasure = converter.convert(command);

        //then
        assertNotNull(unitOfMeasure);
        assertEquals(LONG_VALUE, unitOfMeasure.getId());
        assertEquals(UOM, unitOfMeasure.getUom());

    }


}
