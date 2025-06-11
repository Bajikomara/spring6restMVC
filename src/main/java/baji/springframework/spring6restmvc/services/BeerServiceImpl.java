package baji.springframework.spring6restmvc.services;

import baji.springframework.spring6restmvc.model.Beer;
import baji.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override

    public Beer getBeerById(UUID id) {
        log.debug("get Beer id in service. Id: " + id.toString());
        return Beer.builder()
                .id(id)
                .version(1)
                .beername("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .qunatityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
