package bg.jprogrammersseason2.skyscanner.api.flight_price;

import bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto.SkyscannerDTO;
import bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto.SkyscannerPlacesDTO;
import bg.jprogrammersseason2.skyscanner.api.low_cost_travel.util.PartialDate;
import reactor.core.publisher.Mono;

public interface FlightPriceService
{
  Mono<SkyscannerDTO> getFlightPriceByDateAndLocation(PartialDate outBoundDate, PartialDate inBoundDate, String origin, String destination) ;

  Mono<SkyscannerPlacesDTO> getSkyscannerPlaces(String place);

}
