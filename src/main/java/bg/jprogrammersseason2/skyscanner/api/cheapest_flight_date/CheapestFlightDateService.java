package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date;

import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl.dto.CheapestQuoteDto;
import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_date.impl.dto.LocationsDto;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface CheapestFlightDateService
{
  Mono<CheapestQuoteDto> searchByLocation(String destinationPlace) throws IOException, ExecutionException, InterruptedException;

  Mono<LocationsDto> findDestinations(String query) throws IOException, ExecutionException, InterruptedException;

}
