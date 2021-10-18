package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponse
{
  private String        carrier;
  private PlaceResponse origin;
  private PlaceResponse destination;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime departureDate;
}
