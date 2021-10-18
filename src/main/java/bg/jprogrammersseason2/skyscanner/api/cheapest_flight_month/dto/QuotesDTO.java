package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuotesDTO
{
  private Long          quoteId;
  private Integer       minPrice;
  private Boolean       direct;
  private LegDTO        outBoundLeg;
  private LegDTO        inBoundLeg;
  private LocalDateTime quoteDateTime;
}
