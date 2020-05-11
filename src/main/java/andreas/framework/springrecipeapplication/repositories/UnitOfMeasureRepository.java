package andreas.framework.springrecipeapplication.repositories;

import andreas.framework.springrecipeapplication.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    //Dynamic finder findByUom
    Optional<UnitOfMeasure> findByUom(String uom);
}
