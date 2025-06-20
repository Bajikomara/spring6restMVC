package baji.springframework.spring6restmvc.repositories;

import baji.springframework.spring6restmvc.entities.Beer;
import baji.springframework.spring6restmvc.entities.BeerOrder;
import baji.springframework.spring6restmvc.entities.BeerOrderShipment;
import baji.springframework.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testcustomer;

    Beer testbeer;

    @BeforeEach
    void setUp() {
        testcustomer = customerRepository.findAll().get(0);
        testbeer = beerRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void testBeerOrders() {
        BeerOrder beerorder = BeerOrder.builder()
                .customerRef("Test Order")
                .customer(testcustomer)
                .beerOrderShipment(BeerOrderShipment.builder()
                        .trackingNumber("1235r")
                        .build())
                .build();
        BeerOrder savedOrderBeer = beerOrderRepository.save(beerorder);

        System.out.println(savedOrderBeer.getCustomerRef());

    }
}