package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LegResponse
{
  private String        origin;
  private String        destination;
  private LocalDateTime departureDate;
  private List<String>  carriers;
}
