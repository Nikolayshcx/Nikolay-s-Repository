package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteResponse
{
  private String        price;
  private Boolean        direct;
  private FlightResponse outboundLeg;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private FlightResponse inboundLeg;
}
