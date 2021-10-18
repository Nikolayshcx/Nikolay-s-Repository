package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.consumed_data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightLeg
{
  private List<Integer> carrierIds;
  private Integer       originId;
  private Integer       destinationId;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime departureDate;
}
