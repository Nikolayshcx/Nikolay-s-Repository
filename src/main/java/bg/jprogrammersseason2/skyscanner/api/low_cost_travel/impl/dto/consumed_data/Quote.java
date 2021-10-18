package bg.jprogrammersseason2.skyscanner.api.low_cost_travel.impl.dto.consumed_data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quote
{
  private Integer quoteId;
  private Integer minPrice;
  private Boolean direct;
  private FlightLeg outboundLeg;
  private FlightLeg inboundLeg;
  private LocalDate quoteDateTime;
}
