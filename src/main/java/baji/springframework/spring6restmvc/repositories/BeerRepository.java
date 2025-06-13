package baji.springframework.spring6restmvc.repositories;

import baji.springframework.spring6restmvc.entities.Beer;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Example<? extends Beer> Id(UUID id);
}
