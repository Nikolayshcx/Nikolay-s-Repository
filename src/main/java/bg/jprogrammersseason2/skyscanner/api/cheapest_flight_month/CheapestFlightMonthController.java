package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month;

import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month.dto.QuotesResponse;
import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month.dto.SkyScannerDTO;
import bg.jprogrammersseason2.skyscanner.api.statistics.CheapestFlightMonthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CheapestFlightMonthController
{

  private final CheapestFlightMonthService cheapestFlightMonthService;

  public CheapestFlightMonthController(CheapestFlightMonthService cheapestFlightMonthService)
  {
    this.cheapestFlightMonthService = cheapestFlightMonthService;
  }


  @GetMapping("/cheapest-flight-month")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<QuotesResponse>> getFlightPriceByDateAndLocation(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate month,
                                                                              @RequestParam String origin,
                                                                              @RequestParam String destination,
                                                                              @RequestParam String carrier,
                                                                              @RequestParam int numberOfDays) throws JsonProcessingException
  {

    // Make the call to the skyscanner API
    SkyScannerDTO skyscannerDTO = cheapestFlightMonthService.
        getCheapestFlightForTheMonth(origin, destination, month,carrier);

    // Convert the skyscanner response in user friendly DTOs.
    List<QuotesResponse> quotes = cheapestFlightMonthService.
        convertSkyscannerDTOtoQuotesResponse(skyscannerDTO);

  List<QuotesResponse> matchingCheapestFlights = cheapestFlightMonthService.
        filterResults(quotes, carrier, numberOfDays);

    return ResponseEntity.ok(matchingCheapestFlights);
  }
}
