package baji.springframework.spring6restmvc.Controller;

import baji.springframework.spring6restmvc.entities.Beer;
import baji.springframework.spring6restmvc.model.BeerDTO;
import baji.springframework.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beercontroller;

    @Autowired
    BeerRepository repository;
    @Autowired
    private BeerRepository beerRepository;

    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class, () -> beercontroller.getBeerById(UUID.randomUUID()));

    }

    @Test
    void testGetByID() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO dto = beercontroller.getBeerById(beer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void testListBeers(){
        List<BeerDTO> dtos = beercontroller.listBeers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional // The below  test runs first and deletes the data so we annotate with @Transactional and @Rollbacl
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beercontroller.listBeers();
        assertThat(dtos.size()).isEqualTo(0);

    }
}