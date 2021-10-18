package bg.jprogrammersseason2.skyscanner.api.low_cost_travel;

import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.CheapestFlightServiceImpl;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.QuoteResponse;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.FlightDataMapper;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDate;
import bg.jprogrammersseason2.skyscanner.validation.ValidPartialDate;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1")
@Validated
public class Controller
{
  private final CheapestFlightServiceImpl service;

  public Controller(CheapestFlightServiceImpl service)
  {
    this.service = service;
  }

  @GetMapping("/cheapestFlight")
  @Valid
  public Mono<QuoteResponse> getCheapestFlight(@RequestParam String origin,
                                               @RequestParam String destination,
                                               @ValidPartialDate PartialDate outboundDate,
                                               @ValidPartialDate(required = false) PartialDate inboundDate) throws JsonProcessingException, ExecutionException, InterruptedException
  {
    return service.getCheapestFlight(origin, destination, outboundDate, inboundDate)
                  .map(FlightDataMapper::mapFlightDataToResponse);
  }
}
