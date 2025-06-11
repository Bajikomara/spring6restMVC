package baji.springframework.spring6restmvc.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Beer   {
    private UUID id;
    private Integer version;
    private String beername;
    private BeerStyle beerStyle;
    private String upc;
    private Integer qunatityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

}
