package andreas.framework.springrecipeapplication.services;

import andreas.framework.springrecipeapplication.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listOfAllUnitOfMeasures();
}
