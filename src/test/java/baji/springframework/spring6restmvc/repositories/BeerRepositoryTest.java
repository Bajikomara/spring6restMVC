package baji.springframework.spring6restmvc.repositories;

import baji.springframework.spring6restmvc.bootstrap.BootStrapData;
import baji.springframework.spring6restmvc.entities.Beer;
import baji.springframework.spring6restmvc.model.BeerCSVRecord;
import baji.springframework.spring6restmvc.model.BeerStyle;
import baji.springframework.spring6restmvc.services.BeerCSVServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootStrapData.class, BeerCSVServiceImpl.class})
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByName() {
        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");

        assertThat(list.size()).isEqualTo(336);
    }

    @Test
    void testSaveBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("MY BEER 0123456789 01234567890123456789012345678901234567890123456789012345678901234567890123456789")
                    .beerStyle(BeerStyle.ALE)
                    .upc("784521")
                    .price(new BigDecimal("11.99"))
                    .build());

            beerRepository.flush();
        });

    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("MY BEER")
                        .beerStyle(BeerStyle.ALE)
                        .upc("784521")
                        .price(new BigDecimal("11.99"))
                .build());

        beerRepository.flush();
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}