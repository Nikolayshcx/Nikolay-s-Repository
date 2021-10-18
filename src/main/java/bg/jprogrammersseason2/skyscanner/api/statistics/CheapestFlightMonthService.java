package bg.jprogrammersseason2.skyscanner.api.statistics;


import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month.dto.QuotesResponse;
import bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month.dto.SkyScannerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.util.List;

public interface CheapestFlightMonthService {

  SkyScannerDTO getCheapestFlightForTheMonth(String origin, String destination, LocalDate month,String carrier) throws JsonProcessingException;
  String makeRequest(String origin,String destination, LocalDate month);
  List<QuotesResponse> convertSkyscannerDTOtoQuotesResponse(SkyScannerDTO skyscannerDTO);
  List<QuotesResponse> filterResults(List<QuotesResponse> possibleFlights, String carrier, int numberOfDays);
}
