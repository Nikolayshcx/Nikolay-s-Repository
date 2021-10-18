package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacesDTO
{
  private Long      placeId;
  private String    name;
  private String type;
  private String    skyscannerCode;
}
