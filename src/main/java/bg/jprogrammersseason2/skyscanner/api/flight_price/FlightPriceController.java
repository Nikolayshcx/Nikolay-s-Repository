package bg.jprogrammersseason2.skyscanner.api.flight_price;

import bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto.PlaceDTO;
import bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto.QuotesResponseDTO;
import bg.jprogrammersseason2.skyscanner.api.flight_price.utils.FlightPriceMapper;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDate;
import bg.jprogrammersseason2.skyscanner.validation.ValidPartialDate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class FlightPriceController
{

  private final FlightPriceService flightPriceService;
  private final FlightPriceMapper  flightPriceMapper;

  public FlightPriceController(FlightPriceService flightPriceService, FlightPriceMapper flightPriceMapper)
  {
    this.flightPriceService = flightPriceService;
    this.flightPriceMapper = flightPriceMapper;
  }

  @GetMapping("/flights")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> getFlightPriceByDateAndLocation(
      @ValidPartialDate PartialDate outBoundDate,
      @ValidPartialDate(required = false) PartialDate inBoundDate,
      @RequestParam String origin,
      @RequestParam String destination,
      Model model)
  {

    return  flightPriceService.getFlightPriceByDateAndLocation(outBoundDate, inBoundDate, origin, destination)
                              .map(skyscanner -> {
                      List<QuotesResponseDTO> list = flightPriceMapper.convertSkyscannerDTOtoQuotesResponseDTO(skyscanner);
                      model.addAttribute("list", list);
                      return "flights";
                      });
  }

  @GetMapping("/search-flights")
  @ResponseStatus(HttpStatus.OK)
  public String getIndex()
  {
    return "search-flights";
  }

  @GetMapping("/places")
  public String suggestPlaces(@RequestParam String place, Model model)
  {
    flightPriceService.getSkyscannerPlaces(place)
        .map(skyscanner -> {
          List<PlaceDTO> places = skyscanner.getPlaces();
          model.addAttribute("places", places);
          return skyscanner;
        });

    return "places";
  }


}
