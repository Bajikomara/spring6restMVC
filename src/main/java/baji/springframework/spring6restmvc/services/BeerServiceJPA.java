package baji.springframework.spring6restmvc.services;

import baji.springframework.spring6restmvc.entities.Beer;
import baji.springframework.spring6restmvc.mappers.BeerMapper;
import baji.springframework.spring6restmvc.model.BeerDTO;
import baji.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers(String beerName) {

        List<Beer> beerList;

        if(StringUtils.hasText(beerName)){
            beerList = listBeerByName(beerName);
        }else{
            beerList = beerRepository.findAll();
        }

        return beerList
                .stream()
                .map(beerMapper ::beerTobeerDtoo)
                .collect(Collectors.toList());
    }

    public List<Beer> listBeerByName(String beerName){
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%");
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerTobeerDtoo(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {

        return beerMapper.beerTobeerDtoo(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {

        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            atomicReference.set(Optional.of(beerMapper
                    .beerTobeerDtoo(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public Boolean deleteBeerById(UUID beerId) {

        if(beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {

    }
}
