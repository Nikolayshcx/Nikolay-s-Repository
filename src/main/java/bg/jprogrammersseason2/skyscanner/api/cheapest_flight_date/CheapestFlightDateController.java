package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date;

import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl.dto.CheapestQuoteDto;
import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl.dto.LocationsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/v1")
public class CheapestFlightDateController
{
  CheapestFlightDateService CheapestFlightDateService;

  public CheapestFlightDateController(CheapestFlightDateService CheapestFlightDateService)
  {
    this.CheapestFlightDateService = CheapestFlightDateService;
  }

  @GetMapping("/cheapestTo")
  public Mono<CheapestQuoteDto> findCheapestIn3MonthsToLocation(@RequestParam(defaultValue = "SOF") String destination) throws ExecutionException, InterruptedException, IOException
  {
    return CheapestFlightDateService.searchByLocation(destination);
  }

  @GetMapping("/destinationsFrom")
  public Mono<LocationsDto> listAllLocations(@RequestParam(defaultValue = "SOF") String place) throws IOException, ExecutionException, InterruptedException
  {
    return CheapestFlightDateService.findDestinations(place);
  }


}
