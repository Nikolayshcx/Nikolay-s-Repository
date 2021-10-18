package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkyScannerDTO
{
  private List<QuotesDTO> quotes;
  private List<PlacesDTO> places;
  private List<CarriersDTO> carriers;
}
