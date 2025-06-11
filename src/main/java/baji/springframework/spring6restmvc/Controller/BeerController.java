package baji.springframework.spring6restmvc.Controller;

import baji.springframework.spring6restmvc.model.Beer;
import baji.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

    public Beer getBeerById(UUID id) {
        log.debug("get Beer By Id - in Controller");
        return beerService.getBeerById(id);
    }

}
