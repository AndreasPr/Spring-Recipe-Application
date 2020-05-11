package andreas.framework.springrecipeapplication.repositories;

import andreas.framework.springrecipeapplication.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //That will bring up an embedded database and it is going to configure Spring Data JPA for us
class UnitOfMeasureRepositoryIntegrationTest {

    @Autowired //Dependency Injection on our Integration Test, so Spring Context will start up and we get an instance of UnitOfMeasureRepository
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByUom() {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByUom("Each");
        assertEquals("Each", uomOptional.get().getUom());
    }

    @Test
    void findByUomCup(){
        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByUom("Cup");
        assertEquals("Cup", cupUomOptional.get().getUom());
    }
}
