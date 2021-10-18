package bg.jprogrammersseason2.skyscanner.api.flight_price.impl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LegDTO
{
  private List<Long>    carrierIds;
  private Long          originId;
  private Long          destinationId;
  private LocalDateTime departureDate;
}
