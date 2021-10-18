package bg.jprogrammersseason2.skyscanner.api.cheapest_flight_month.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuotesResponse
{
  private Long          quoteId;
  private Integer       price;
  private Boolean       direct;
  private LegResponse   outBoundLeg;
  private LegResponse   inBoundLeg;
  private LocalDateTime quoteDateTime;

  public QuotesResponse(Long quoteId, Integer price, Boolean direct, LegResponse outBoundLeg, LegResponse inBoundLeg, LocalDateTime quoteDateTime)
  {
    this.quoteId = quoteId;
    this.price = price;
    this.direct = direct;
    this.outBoundLeg = outBoundLeg;
    this.inBoundLeg = inBoundLeg;
    this.quoteDateTime = quoteDateTime;
  }
}
