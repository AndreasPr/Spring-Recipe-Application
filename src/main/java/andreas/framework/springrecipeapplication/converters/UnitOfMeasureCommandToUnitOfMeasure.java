package andreas.framework.springrecipeapplication.converters;

import andreas.framework.springrecipeapplication.commands.UnitOfMeasureCommand;
import andreas.framework.springrecipeapplication.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;


@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    // @Synchronized: To avoid multiple threads using a method
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source){
        if(source == null){
            return null;
        }

        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(source.getId());
        uom.setUom(source.getUom());
        return uom;
    }
}
