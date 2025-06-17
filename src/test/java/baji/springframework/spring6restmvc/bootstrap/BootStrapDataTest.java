package baji.springframework.spring6restmvc.bootstrap;

import baji.springframework.spring6restmvc.repositories.BeerRepository;
import baji.springframework.spring6restmvc.repositories.CustomerRepository;
import baji.springframework.spring6restmvc.services.BeerCSVService;
import baji.springframework.spring6restmvc.services.BeerCSVServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BeerCSVServiceImpl.class)
class BootStrapDataTest {

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCSVService beerCSVService;

    BootStrapData bootStrapData;

    @BeforeEach
    void setUp() {
        bootStrapData = new BootStrapData(beerRepository, customerRepository, beerCSVService);
    }

    @Test
    void testrun()throws Exception {
        bootStrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}