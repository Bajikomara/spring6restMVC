package baji.springframework.spring6restmvc.Controller;

import baji.springframework.spring6restmvc.entities.Beer;
import baji.springframework.spring6restmvc.mappers.BeerMapper;
import baji.springframework.spring6restmvc.model.BeerDTO;
import baji.springframework.spring6restmvc.repositories.BeerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.core.Is.is;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beercontroller;

    @Autowired
    BeerRepository repository;

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testListBeerbyName() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .queryParam("beerName", "IPA"))
                .andExpect(jsonPath("$.size()",is(100)));
    }

    //    @Test
//    void testPatchBeerBadName() throws Exception {
//        Beer beer = beerRepository.findAll().get(0);
//
//        Map<String, Object> beerMap = new HashMap<>();
//        beerMap.put("beerName", "New Name 1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
//
//        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(beerMap)))
//                .andExpect(status().isBadRequest());
//
//    }

    @Test
    void testDeleteByIDNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beercontroller.deleteById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deletebyIdFound() {
        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity responseEntity = beercontroller.deleteById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(beerRepository.findById(beer.getId()).isEmpty());

//        Beer foundBeer = beerRepository.findById(beer.getId()).get();
//        assertThat(foundBeer).isNull();
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
           beercontroller.updateById(UUID.randomUUID(),BeerDTO.builder().build());
        });
    }

    @Test
    void updateExistingBeer() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerTobeerDtoo(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "Updated";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beercontroller.updateById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer UpdatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(UpdatedBeer.getBeerName()).isEqualTo(beerName);
    }

    @Rollback
    @Transactional
    @Test
    void saveNewBeertest() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity responseEntity = beercontroller.handlepost(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));

        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID  = responseEntity.getHeaders().getLocation().getPath().split("/");

        UUID savedId = UUID.fromString(locationUUID[4]);

        Beer beer = repository.findById(savedId).get();
        assertThat(beer).isNotNull();
    }

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
        List<BeerDTO> dtos = beercontroller.listBeers(null);
        assertThat(dtos.size()).isEqualTo(2413);
    }

    @Rollback
    @Transactional // The below  test runs first and deletes the data so we annotate with @Transactional and @Rollbacl
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beercontroller.listBeers(null);
        assertThat(dtos.size()).isEqualTo(0);

    }
}