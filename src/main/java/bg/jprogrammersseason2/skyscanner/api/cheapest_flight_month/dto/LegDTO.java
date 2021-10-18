package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LegDTO
{
  private List<Long> carrierIds;
  private Long       originId;
  private Long          destinationId;
  private LocalDateTime departureDate;
}
