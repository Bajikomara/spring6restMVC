package baji.springframework.spring6restmvc.mappers;

import baji.springframework.spring6restmvc.entities.Beer;
import baji.springframework.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerTobeerDtoo(Beer beer);


}
