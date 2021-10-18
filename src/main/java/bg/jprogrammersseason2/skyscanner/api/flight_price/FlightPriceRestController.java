package bg.jprogrammersseason2.skyscanner.api.flight_price;

import bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto.PlaceDTO;
import bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto.QuotesResponseDTO;
import bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto.SkyscannerPlacesDTO;
import bg.jprogrammersseason2.skyscanner.api.flight_price.utils.FlightPriceMapper;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDate;
import bg.jprogrammersseason2.skyscanner.validation.ValidPartialDate;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v2")
public class FlightPriceRestController
{

  private final FlightPriceService flightPriceService;
  private final FlightPriceMapper  flightPriceMapper;

  public FlightPriceRestController(FlightPriceService flightPriceService, FlightPriceMapper flightPriceMapper)
  {
    this.flightPriceService = flightPriceService;
    this.flightPriceMapper = flightPriceMapper;
  }

  @GetMapping("/flights")
  @ResponseStatus(HttpStatus.OK)
  public Mono<List<QuotesResponseDTO>> getFlightPriceByDateAndLocation(
      @ValidPartialDate PartialDate outBoundDate,
      @ValidPartialDate(required = false) PartialDate inBoundDate,
      @RequestParam String origin,
      @RequestParam String destination)
  {
    return
        flightPriceService.getFlightPriceByDateAndLocation(outBoundDate, inBoundDate, origin, destination).
            map(flightPriceMapper::convertSkyscannerDTOtoQuotesResponseDTO);
  }

  @GetMapping("/places")
  public Mono<List<PlaceDTO>> suggestPlaces(@RequestParam String place)
  {
    return flightPriceService.getSkyscannerPlaces(place).map(
        SkyscannerPlacesDTO::getPlaces
    );
  }


}
