package baji.springframework.spring6restmvc.Controller;

import baji.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BeerController {
    private final BeerService beerService;

}
